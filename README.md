# Risk Game
- Concordia University
- SOEN 6441 - Advanced Programming Practices, Fall 2023
- Group Project: W10

## Development Environment Prerequisite
- JDK 11
- Maven

## Usage
### To build the project
```sh
// This command will build the project and run the unit tests
// The build and report artifacts are located inside the `target` folder

$ mvn -B package --file pom.xml
```

### To run unit tests
```sh
$ mvn surefire:test
```

### To generate Javadoc documentation
```sh
// The output folder is located at target/site/apidocs
// Open the index.html file inside the output folder to see the doc

$ mvn javadoc:javadoc
```

### Code formatting
#### For VSCode Editor
Install [EditorConfig for VS Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig) on your editor. You should enable the `Enable On Save` option. Once they are done, VSCode will read `.editorconfig` file and format accordingly.

#### For IntelliJ IDEA
`.editorconfig` support is already built-in for IntelliJ IDEA.

#### CLI usage
The project uses Spotless with Google Java Format for code layout formatting.

```sh
// To check the violations on code formatting
$ mvn spotless:check

// To apply code formatting on the whole project
$ mvn spotless:apply
```

## Main Dependencies
- Package manager: Maven
- Unit testing framework: JUnit
- Code formatting: Spotless, Google Java Format, Editorconfig
