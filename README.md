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
    target/thread-introspector-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```

## Usage

To run the application, use the following command:

```sh
java -jar target/thread-introspector-1.0-SNAPSHOT-jar-with-dependencies.jar -p <pid> -i <interval-in-seconds> -l <logLevel>
