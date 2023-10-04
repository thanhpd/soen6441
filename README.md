# Risk Game
- Concordia University
- SOEN 6441 - Advanced Programming Practices, Fall 2023
- Group Project: W10

## Table of Contnent
* [Group Members](#group-members)
* [Instructions](#instructions)
	+ [Development Environment Prerequisite](#development-environment-prerequisite)
	+ [Development Commands](#development-commands)
		- [To build and run the project](#to-build-and-run-the-project)
		- [To run unit tests](#to-run-unit-tests)
		- [To generate Javadoc documentation](#to-generate-javadoc-documentation)
	+ [Code formatting](#code-formatting)
		- [For VSCode Editor](#for-vscode-editor)
		- [For IntelliJ IDEA](#for-intellij-idea)
		- [CLI usage](#cli-usage)
	+ [Main Dependencies](#main-dependencies)

## Group Members
- Omnia Alam (AlamOmnia)
- Yajing Liu (YajingLiu2357)
- Sherwyn D'souza (sherwyn11)
- Darlene Nazareth (Darlene-Naz)
- Duy Thanh Phan (thanhpd)
- Md Tazin Morshed Shad (TazinMorshed)

## Instructions
### Development Environment Prerequisite
- JDK 11
- Maven

### Development Commands
All commands are run inside the project folder.

#### To build and run the project
```sh
// This command will build the project and run the unit tests
// The build and report artifacts are located inside the `target` folder

$ mvn -B package

// Run the executable file via Maven
$ mvn exec:java
```

#### To run unit tests
```sh
$ mvn test
```

#### To generate Javadoc documentation
```sh
// The Javadoc folder for the tested code is located at `docs/apidocs/`
// The Javadoc folder for the test code is located at `docs/apidocs/testapidocs`
// Open the index.html file inside the output folder to see the document

$ mvn javadoc:javadoc javadoc:test-javadoc
```

### Code formatting
#### For VSCode Editor
Install [EditorConfig for VS Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig) on your editor. You should enable the `Enable On Save` option. Once they are done, VSCode will read `.editorconfig` file and format accordingly.

#### For IntelliJ IDEA
`.editorconfig` support is already built-in for IntelliJ IDEA.

#### CLI usage
The project uses Spotless for code layout formatting.

```sh
// To check the violations on code formatting
$ mvn spotless:check

// To apply code formatting on the whole project
$ mvn spotless:apply
```

### Main Dependencies
- Package manager: Maven
- Unit testing framework: JUnit
- Code formatting: Spotless, Google Java Format, Editorconfig
