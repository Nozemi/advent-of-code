# Advent of Code Solutions
This project will house my solutions for [Advent of Code](https://adventofcode.com/).

### Usage
If for whatever reason you want to use my code to test your own input, follow the steps below.
1. Clone this repository.
2. Create appropriate folders in the [resources](/src/main/resources)-directory.\
    Folder structure is as following:
   - [resources](/src/main/resources)
     - puzzle-input
       - year-... 
       - year-2021
         - day01-input.txt
         - day02-input.txt
         - ...-input.txt
       - year-...
3. Then populate the needed puzzles with its data.\
   Puzzles that doesn't have an input file, will be skipped with an error message.
4. Run the Gradle task called `run`. If you want to run for multiple years, you need to specify a comma separated list of years. Example: `2021,2022,2023`