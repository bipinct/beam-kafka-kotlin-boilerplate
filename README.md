# Apache Beam - Kafka - Kotlin Boilerplate

## Project Overview
This project is a boilerplate setup for Kafka and Apache Beam integration, built using Gradle and Kotlin. It leverages Apache Beam for data processing and Kafka for messaging.

## BigQuery Integration Sample
This project includes a sample program for writing data to Google BigQuery using Apache Beam. The code resides in a file named `MainBigqueryWrite.kt`. Below is a brief overview of the functionality:

The program demonstrates how to:
- Configure Google Cloud Platform (GCP) settings such as the project ID and temporary bucket location.
- Define a pipeline using Apache Beam.
- Write data to a BigQuery table with schema and table specifications.

Key configurations:
- **Write Disposition**: `WRITE_TRUNCATE` (overwrites existing data).
- **Create Disposition**: `CREATE_IF_NEEDED` (creates the table if it doesn’t exist).

Refer to `MainBigqueryWrite.kt` for the complete implementation.

---

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

## Kafka Server Details
Download source:
https://downloads.apache.org/kafka/3.9.0/kafka_2.12-3.9.0.tgz

Unzip and navigate to the `bin` directory, then run the following commands to start the Kafka server:

```bash
./kafka-server-start.sh ../config/server.properties
./kafka-console-producer.sh --broker-list localhost:9092 --topic bct --property "parse.key=true" --property  "key.separator=:"
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic bct --from-beginning
```

## License
This project is licensed under the Apache License, Version 2.0.

