@ECHO OFF
echo Installing Enigma
mkdir c:\enigma-data
mkdir c:\enigma-data\log
mkdir c:\enigma-data\db
echo Copying files for Swiss Ephemeris
xcopy /S /Q enigma-data\* c:\enigma-data
start "Install Enigma" /wait msiexec.exe /i Enigma-2020.1.0.msi
echo Installation completed
echo Did you install the fonts as Administrator ?
echo Press a key to finish
pause
