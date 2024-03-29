@ECHO OFF
rem This script creates an installable package of Enigma. It is called directly
rem from pom.xml. To build the package run 'mvn clean install' .
rem The script is an adaptation of the script by Dirk Lemmermann, see :
rem https://github.com/dlemmermann/JPackageScriptFX
rem ------ ENVIRONMENT --------------------------------------------------------
rem The script depends on various environment variables to exist in order to
rem run properly. The java version we want to use, the locationOld of the java
rem binaries (java home), and the project version as defined inside the pom.xml
rem file, e.g. 1.0-SNAPSHOT.
rem
rem PROJECT_VERSION: version used in pom.xml, e.g. 1.0-SNAPSHOT
rem APP_VERSION: the application version, e.g. 1.0.0, shown in "about" dialog

set JAVA_VERSION=14
set MAIN_JAR=enigma-%PROJECT_VERSION%.jar

rem ------ SETUP DIRECTORIES AND FILES ----------------------------------------
rem Remove previously generated java runtime and installers. Copy all required
rem jar files into the input/libs folder.

IF EXIST target\java-runtime rmdir /S /Q  .\target\java-runtime
IF EXIST target\installer rmdir /S /Q target\installer

xcopy /S /Q target\libs\* target\installer\input\libs\
copy target\%MAIN_JAR% target\installer\input\libs\

rem ------ REQUIRED MODULES ---------------------------------------------------
rem Use jlink to detect all modules that are required to run the application.
rem Starting point for the jdep analysis is the set of jars being used by the
rem application.

echo detecting required modules

"%JAVA_HOME%\bin\jdeps" ^
  --multi-release %JAVA_VERSION% ^
  --ignore-missing-deps ^
  --class-path "target\installer\input\libs\*" ^
  --print-module-deps target\classes\com\radixpro\enigma\App.class > temp.txt

set /p detected_modules=<temp.txt

echo detected modules: %detected_modules%

rem ------ RUNTIME IMAGE ------------------------------------------------------
rem Use the jlink tool to create a runtime image for our application. We are
rem doing this is a separate step instead of letting jlink do the work as part
rem of the jpackage tool. This approach allows for finer configuration and also
rem works with dependencies that are not fully modularized, yet.

echo creating java runtime image

call "%JAVA_HOME%\bin\jlink" ^
  --no-header-files ^
  --no-man-pages ^
  --compress=2 ^
  --strip-debug ^
  --add-modules %detected_modules% ^
  --output target/java-runtime


rem ------ PACKAGING ----------------------------------------------------------
rem A loop iterates over the various packaging types supported by jpackage. In
rem the end we will find all packages inside the target/installer directory.

for %%s in ("msi") do call "%JAVA_HOME%\bin\jpackage" ^
  --type %%s ^
  --dest target/installer ^
  --input target/installer/input/libs ^
  --name Enigma ^
  --main-class com.radixpro.enigma.AppLauncher ^
  --main-jar %MAIN_JAR% ^
  --java-options -Xmx2048m ^
  --runtime-image target/java-runtime ^
  --icon src/main/resources/img/enigma-icon.ico ^
  --app-version %APP_VERSION% ^
  --win-shortcut ^
  --win-menu ^
  --win-menu-group Enigma ^
  --copyright "Copyright © 2020 Jan Kampherbeek."
