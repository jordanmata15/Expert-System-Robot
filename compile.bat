:: USAGE
::      Run this script to compile

set PACKAGE_DIR=%~dp0


set BUILD_DIR=%PACKAGE_DIR%\\build
set SRC_DIR=%PACKAGE_DIR%\\src
set PROGRAM_NAME=ExpertSystem

cd %SRC_DIR%
javac -d %BUILD_DIR% *.java rules\\*.java

cd %PACKAGE_DIR%