# Apache Beam - Kafka - Kotlin boilerplate

## Project Overview
This project is a boilerplate setup for Kafka and Apache Beam integration, built using Gradle and Kotlin. It leverages Apache Beam for data processing and Kafka for messaging.

## Package Versions

### Gradle
- **Gradle Version**: 8.6
  - Referenced in `gradle-wrapper.properties`:
    ```
    distributionUrl=https\://services.gradle.org/distributions/gradle-8.6-bin.zip
    ```

### Kotlin
- **Kotlin Version**: 1.9.23
  - Referenced in `build.gradle.kts`:
    ```kotlin
    kotlin("jvm") version "1.9.23"
    ```

### Java
- **Java Version**: 21
  - Referenced in `.idea/misc.xml`:
    ```xml
    <option name="project-jdk-name" value="21" />
    ```

### Apache Beam
- **Apache Beam Version**: 2.61.0
  - Referenced in `build.gradle.kts`:
    ```kotlin
    val beamVersion = "2.61.0"
    ```

### Kafka
- **Kafka Clients Version**: 3.9.0
  - Referenced in `build.gradle.kts`:
    ```kotlin
    implementation("org.apache.kafka:kafka-clients:3.9.0")
    ```

### Kafka for Testing
- **Kafka Version**: kafka_2.12-3.9.0
  - Used for testing purposes.

### Confluent Kafka Avro Serializer
- **Version**: 7.6.0
  - Referenced in `build.gradle.kts`:
    ```kotlin
    implementation("io.confluent:kafka-avro-serializer:7.6.0")
    ```

### Guava
- **Version**: 32.0.1-jre
  - Referenced in `build.gradle.kts`:
    ```kotlin
    implementation("com.google.guava:guava:32.0.1-jre")
    ```

### Testing Framework
- **Kotlin Test Version**: (latest compatible with Kotlin 1.9.23)
  - Referenced in `build.gradle.kts`:
    ```kotlin
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    ```

## Build and Run
To build and run the project, use the following commands:

```bash
./gradlew build
./gradlew run
```

## Kafka Server details
download source
https://downloads.apache.org/kafka/3.9.0/kafka_2.12-3.9.0.tgz
unzip and navigate to bin directory and run following commands to start kafka server 
```bash
./kafka-server-start.sh ../config/server.properties
./kafka-console-producer.sh --broker-list localhost:9092 --topic bct --property "parse.key=true" --property  "key.separator=:"
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic bct --from-beginning
```

## License
This project is licensed under the Apache License, Version 2.0.