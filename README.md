# Expert-System-Robot

This program will use a rule based expert system to navigate a grid and find the goal.
If run with no paramters, the program will display the start and end result to console once finished and output the resulting grid to ./output/output.txt.

You can specify input and output file yourself if you wish using the `-i <filename>` and `-o <filename>` flags. For a full list of flags you can use, run the program with the `--help` flag.

## Running
### IDE (no need to compile)
- Open the package in an IDE (VSCode, Eclipse, etc)
- Run/debug using the IDE (it will do compilation for you)

### Unix
- Compile the program (see below)
- Navigate to the ./build folder (do NOT go into ./build/src)
- Run `java src.ExpertSystem`
- Pass in any flags at the end of the line.</br>
  For example, to run with an input file (-f flag) and pretty printing (-p flag):</br>
  Eg. `java src.ExpertSystem -p -f ../input/input_10.txt`
  
### Windows
- Compile the program (see below)
- Navigate to the .\build folder (do NOT go into .\build\src)
- Run `java src.ExpertSystem`
- Note:</br>
  You can pass in any flags at the end of the line.</br>
  For example, to run with an input file (-f flag) and pretty printing (-p flag):</br>
  Eg. `java src.ExpertSystem -p -f ..\\input\\input_10.txt`
  To run with an input file (-f flag) and an output file:</br>
  Eg. `java src.ExpertSystem -o ..\output\output.txt -f ..\\input\\input_10.txt`
- Alternatively, you can just run the .\run.bat file and pass in parameters directly there
 
 
## Compiling
### Windows (Using batch scripts)
- Navigate to the src directory
- Run `javac -d ..\\build *.java rules\\*.java`
- Alternatively, you could just run the .\compile.bat file

### Unix (Using makefile)
- Navigate to the root of the package
- type `make`


# Usage
ExpertSystem:</br>
>    Accepted Flags:</br>
>>        -d               Display grid after each move</br>
>>        -p               Display grids with pretty printing (better spacing and more intuitive object labels)</br>
>>        -r               Display all rules fired after finished</br>
>>        -g <percent>     Percent of the board that should be obstacles [0-100] (eg. pass in 10 for 10%)
>>        -i <filename>    Full path to the input file we wish to initialize our board to</br>
>>        -o <filename>    Full path to the output file we wish to write the output to
