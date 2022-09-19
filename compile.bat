:: USAGE
::      Set PACKAGE_DIR to the root of the package on your system
::      Run this script

set PACKAGE_DIR=C:\\Users\\Jordan\\Git\\Expert-System-Robot


set BUILD_DIR=%PACKAGE_DIR%\\build
set SRC_DIR=%PACKAGE_DIR%\\src
set PROGRAM_NAME=ExpertSystem

cd %SRC_DIR%
javac -d %BUILD_DIR% *.java rules\\*.java

cd %PACKAGE_DIR%