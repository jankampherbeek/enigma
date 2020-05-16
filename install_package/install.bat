@ECHO ON
echo Install Enigma
echo Installing Enigma
mkdir c:\enigma-data
mkdir c:\enigma-data\log
mkdir c:\enigma-data\db
echo Copying files for Swiss Ephemeris
xcopy /S /Q enigma-data\* c:\enigma-data
echo Starting installation of software
start "Install Enigma" /wait msiexec.exe /i Enigma-1.1.0.msi
echo Installation completed
pause
