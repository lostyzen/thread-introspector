# Thread Introspector

## Overview

Thread Introspector is a Java application that attaches to a running JVM process, inspects thread states, and provides a summary of the number of threads in various states at regular intervals. Additionally, it allows the user to kill blocked threads upon request.

## Features

- Attaches to a target JVM using its PID.
- Displays a summary of thread states at specified intervals.
- Allows the user to kill blocked threads.
- Configurable log levels.
- Packaged as a FatJar for easy deployment.

## Requirements

- Java 11 or higher.
- Maven for building the project.

## Installation

1. Clone the repository:
    ```sh
    git clone <repository-url>
    cd thread-introspector
    ```

2. Build the project using Maven:
    ```sh
    mvn clean package
    ```

3. The FatJar will be generated in the `target` directory:
    ```sh
    thread-introspector-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```

## Usage

To run the application, use the following command:

```sh
java -jar thread-introspector-1.0-SNAPSHOT-jar-with-dependencies.jar -p <pid> -i <interval-in-seconds> -l <logLevel>
```

## Command Line Options
- -p, --pid : Process ID of the target JVM (required).
- -i, --interval : Interval in seconds to display thread info (required).
- -l, --logLevel : Log level (optional, default: DEBUG). Possible values: DEBUG, INFO, WARN,

### Example
To monitor a JVM process with PID 1234, display thread information every 10 seconds, and set the log level to INFO:

```sh
java -jar target/thread-introspector-1.0-SNAPSHOT-jar-with-dependencies.jar -p 1234 -i 10 -l INFO

```

## Logging
The application uses SLF4J with Logback for logging. The log configuration can be modified in src/main/resources/logback.xml.

### logback.xml
```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>

```
## Development
### Adding Unit Tests
1. Ensure that the project has JUnit configured in pom.xml:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```
2. Create unit tests for new features, such as command line options and log level settings.

### Contributing
1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature-branch).
5. Create a new Pull Request.

## Licence
This project is licensed under the Apache 2.0 License.