# Risk Game
- Concordia University
- SOEN 6441 - Advanced Programming Practices, Fall 2023
- Group Project: W10

## Development Environment Prerequisite
- JDK 11
- Maven

## Usage
### To build the project
```
// This command will build the project and run the unit tests
// The build and report artifacts are located inside the `target` folder

$ mvn -B package --file pom.xml
```

### To run unit tests
```
$ mvn surefire:test
```

### To generate Javadoc documentation
```
// The output folder is located at target/site/apidocs
// Open the index.html file inside the output folder to see the doc

$ mvn javadoc:javadoc
```

## Main Dependencies
- Package manager: Maven
- Unit testing framework: JUnit

