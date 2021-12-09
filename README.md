# Advent of Code Solutions
[![license][license-badge]][mit] [![build][ci-badge]](https://github.com/nozemi/advent-of-code/actions?query=workflow%3Abuild)

This is my over-engineered [Advent of Code][aoc-url] (AoC) project written in Kotlin. I've decided to use this project as an opportunity to become a better developer as well as getting to know more of Kotlin. I've also taken advantage of GitHub Actions, which is also very useful for various things.

<hr>

### I Keep Code for Initial Solution
For every puzzle I solve, I will commit the initial code at the time a solution is reached for each part. That way we're able to see the difference in my code when it's written just to work vs when it is written to be efficient and tidy. Not all of my puzzle implementations will have substantial rewrites, that depends on the free time I have to do so as well as how bad it ended up at the time of solving.

## Table of contents
- [Usage](#usage) - How to use this project to test your own input.
- [Solved Puzzles](#solved-puzzles) - A table showing puzzles solvable with this codebase.
- [Features](#features) - Features I've implemented aside from puzzles.
  - [Advent of Code input Downloader](#advent-of-code-input-downloader)
  - [Solved Puzzles Table Generator](#solved-puzzles-table-generator)
  - [command-line-interface](#command-line-interface)
  
<hr>

## Usage
If you want to use my project to test your input, you can do so by cloning my repo. If you don't intend to manually fetch the puzzle input data, you can setup the `.env`-file with a token from [AoC][aoc-url]. This token is found in the `session`-cookie.

If you intend to manually fetch the puzzle input data, you follow this folder-structure: `data/inputs/<YEAR>/<DAY>.txt`, example: `data/inputs/2021/day01.txt`.

I've added a few Gradle-tasks for the sake of convenience. They can be found under `application`.
- `runThisYear` - Runs every implemented day from this year's puzzle, no need to provide any user inputs.
- `runToday` - Runs today's puzzle, if implemented, and there is no need to provide any user input.
- `runWithInput` - Just a task to allow you to run it from Gradle and still accept user input.

You can otherwise run the [Application](/src/main/kotlin/io/nozemi/aoc/Application.kt)-class directly. This however requires you to provide some user input.

## Solved Puzzles
Below is a table showing which statuses for each day of each year.
- ❌ means neither of the two puzzles have working implementations.
- 🚧 means part one is done. Part two is either not started or has an incomplete implementation.
- ✅ means fully implemented and working.

| Days  | [2021][2021]   | [2020][2020]   |
|-------|--------------|--------------|
|  1    | ✅           | ✅           |
|  2    | ✅           | ✅           |
|  3    | ✅           | ✅           |
|  4    | ✅           | ❌           |
|  5    | ✅           | ❌           |
|  6    | ✅           | ❌           |
|  7    | ✅           | ❌           |
|  8    | ✅           | ❌           |
|  9    | ✅           | ❌           |
| 10    | ❌           | ❌           |
| 11    | ❌           | ❌           |
| 12    | ❌           | ❌           |
| 13    | ❌           | ❌           |
| 14    | ❌           | ❌           |
| 15    | ❌           | ❌           |
| 16    | ❌           | ❌           |
| 17    | ❌           | ❌           |
| 18    | ❌           | ❌           |
| 19    | ❌           | ❌           |
| 20    | ❌           | ❌           |
| 21    | ❌           | ❌           |
| 22    | ❌           | ❌           |
| 23    | ❌           | ❌           |
| 24    | ❌           | ❌           |
| 25    | ❌           | ❌           |

[2020]: https://adventofcode.com/2020
[2021]: https://adventofcode.com/2021

## Features
I've implemented some features for my [AoC][aoc-url] project I find very useful. You can read more about each particular feature below.

### Advent of Code Input Downloader
This tool will simply automagically download inputs from the [AoC][aoc-url]-website. Though there is one prerequisite; a session token needs to be provided. This happens through `.env`-file or environment variables from the operating system. Alternatively it can be launched in command line interface mode, and you will have the ability to manually provide the token.

Tool is extremely simple, so it's more of a convenience than a learning opportunity for this particular one. However, it has proven to be very convenient. I just need to start writing code for each day, and then when I run it, it'll automatically download the input for me.

### Solved Puzzles Table Generator
I decided to create a table showing completion status for puzzles. This was initially done manually. Then I figured why not automate the entire thing. There is an API for [AoC][aoc-url] that lets me get my completion statuses, however I figured I'd rather just use my own code to test solvability, rather than what I've completed. This table is related to this codebase, not me as a person anyway, so this approach makes more sense.

What I've done is to create another folder in the [data](/data)-folder. This folder follows the same structure as the normal inputs folder. However, I've noticed that sometimes my solutions will work on example data, but not on actual data - probably because example data lacks the edge cases. To battle this I decided to have two data files; `day00.txt` and `day00-actual.txt`, where the latter one is my actual data, and it's optional.

I also needed to know the expected answer from the data that was provided. Which I solved by adding `[PART1_ANSWER][PART2_ANSWER]` to the top of the data. So first line is always the expected answers to the data below.

### Command Line Interface
While this is more or less useless, I thought it made sense to add this. Considering I treat this project more or less like a real life application, I wanted to add some usability to it, other than keeping everything hardcoded or config based.

The command line interface will ask for year(s), then day(s) and then an optional token (for downloading puzzle data automatically from [AoC][aoc-url]-website).

Valid formats for years and days input is `comma sparated list of numbers` and/or `ranges of numbers`. A token is expected to be exactly 96 characters long, otherwise it won't be valid.


[aoc-url]:https://adventofcode.com/
[mit]: https://opensource.org/licenses/MIT
[license]: /LICENSE.md

[license-badge]: https://img.shields.io/badge/license-MIT-informational
[ci-badge]: https://github.com/nozemi/advent-of-code/actions/workflows/gradle-build.yml/badge.svg