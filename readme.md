Hi!

This is a bunch of koans which can guide you through the process of creating parser combinator in Kotlin from scratch.

This is by no means a course on parsers but more of a very quick intro into the subject. 
To start with, ask yourself the following questions (this is not a quiz, so not being sure about the answer is perfectly fine):
 - What is a grammar? Context-free grammar?
 - What is a parser? Parser combinator?
 - What is left recursion?
 - What is the difference between parse tree and abstract syntax tree?
 - How would you write an interpreter to evaluate expressions like "(1 + 2) * 3 - 4"?


### How to use these koans?
 - compile the project using Gradle `compileTestKotlin` task or IDE
 - take a look at `src/test/parserkoans/1 - Basic parsers/0 - Parser API.kt` to get familiar with core classes
 - open `src/test/parserkoans/1 - Basic parsers/1 - String parser.kt`
    - run the tests to see them fail with `NotImplementedError`
    - make the first test pass by implementing `TODO()`
    - make the second test pass by adding more functionality
    - repeat until all tests in the step are passing
 - repeat the above for the next steps


### See also
 - [Parser combinator koans as a Stepik course](https://github.com/dkandalov/Parser_Combinator_Koans)
 - [Parser4k](https://github.com/fork-handles/forkhandles/tree/trunk/parser4k) - recursive descent parser combinator library for Kotlin with support for left recursion
