plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"
val beamVersion = "2.61.0"

repositories {
    mavenCentral()
    maven(url = "https://packages.confluent.io/maven/")
}

dependencies {
    implementation("com.google.guava:guava:32.0.1-jre")
    implementation("io.confluent:kafka-avro-serializer:7.6.0")
    implementation("org.apache.beam:beam-sdks-java-core:$beamVersion")
    implementation("org.apache.beam:beam-runners-direct-java:$beamVersion")
    implementation("org.apache.beam:beam-sdks-java-io-kafka:$beamVersion")
    implementation("org.apache.kafka:kafka-clients:3.9.0")

// below libraries are required to add bigquery support
    implementation("org.apache.beam:beam-sdks-java-io-google-cloud-platform:$beamVersion")
    implementation("com.google.cloud:google-cloud-bigquery:2.45.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}