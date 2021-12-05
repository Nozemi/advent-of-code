# Advent of Code Solutions
[![license][license-badge]][mit] [![build][ci-badge]](https://github.com/nozemi/advent-of-code/actions?query=workflow%3Abuild)

This project will house my solutions for [Advent of Code][aoc-url] (AoC). I've decided to use this project to experiment with various functionalities. Such as creating a console interface for it, so it can be used more dynamically and outside an IDE. I treat this project as a learning opportunity both in the way I shape the solution, and all the different problems I have to solve in [AoC][aoc-url].

### Usage
If you want to use my project to test your input, you can do so by cloning my repo. If you don't intend to manually fetch the puzzle input data, you can setup the `.env`-file with a token from [AoC][aoc-url]. This token is found in the `session`-cookie.

If you intend to manually fetch the puzzle input data, you follow this folder-structure: `data/inputs/<YEAR>/<DAY>.txt`, example: `data/inputs/2021/day01.txt`.

I've added a few Gradle-tasks for the sake of convenience. They can be found under `application`.
- `runThisYear` - Runs every implemented day from this year's puzzle, no need to provide any user inputs.
- `runToday` - Runs today's puzzle, if implemented, and there is no need to provide any user input.
- `runWithInput` - Just a task to allow you to run it from Gradle and still accept user input.

You can otherwise run the [Application](/src/main/kotlin/io/nozemi/aoc/Application.kt)-class directly. This however requires you to provide some user input.

### Solved Puzzles
Below is a table showing which statuses for each day of each year.
- ❌ means neither of the two puzzles have working implementations.
- 🚧 means part one is done. Part two is either not started or has an incomplete implementation.
- ✅ means fully implemented and working.

{{SOLVED_PUZZLES_TABLE}}

[aoc-url]:https://adventofcode.com/
[mit]: https://opensource.org/licenses/MIT
[license]: /LICENSE.md

[license-badge]: https://img.shields.io/badge/license-MIT-informational
[ci-badge]: https://github.com/nozemi/advent-of-code/actions/workflows/gradle-build.yml/badge.svg

[2020]: https://adventofcode.com/2020
[2021]: https://adventofcode.com/2021