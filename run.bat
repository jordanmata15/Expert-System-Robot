:: USAGE
::      Assumes you have java set in your PATH
::      Run this script
::      Pass in args if you wish as a single quoted argument
::          eg. For pretty printed output (-p flag) using a file (-f flag) 
::              then run the following:
::                  run.bat "-p -f C:\\Users\\Jordan\\Git\\Expert-System-Robot\\input\\input_10.txt"

set PACKAGE_DIR=%~dp0

set BUILD_DIR=%PACKAGE_DIR%\\build
set SRC_DIR=%PACKAGE_DIR%\\src
set PROGRAM_NAME=ExpertSystem

cd %BUILD_DIR%
java src.%PROGRAM_NAME% %~1

cd %PACKAGE_DIR%