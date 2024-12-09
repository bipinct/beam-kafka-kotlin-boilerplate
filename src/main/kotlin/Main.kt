package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// https://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or https://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

import org.apache.beam.sdk.Pipeline

import org.apache.beam.sdk.io.kafka.KafkaIO
import org.apache.beam.sdk.io.kafka.KafkaRecord
import org.apache.beam.sdk.options.Default
import org.apache.beam.sdk.options.Description
import org.apache.beam.sdk.options.PipelineOptions
import org.apache.beam.sdk.options.PipelineOptionsFactory
import org.apache.beam.sdk.transforms.MapElements
import org.apache.beam.sdk.transforms.SimpleFunction
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.apache.beam.sdk.options.Validation.Required


class KafkaTransform : SimpleFunction<KafkaRecord<String, String>, String>() {
    private val logger = LoggerFactory.getLogger(KafkaTransform::class.java)

    override fun apply(element: KafkaRecord<String, String>): String {
        val key = element.kv.key ?: "null"
        val value = element.kv.value
        print("key is: $key and value: $value \n")
        logger.info("Received Kafka message - Key: $key, Value: $value")
        logger.info("Offset: ${element.offset}, Timestamp: ${element.timestamp}")
        return value
    }
}


interface KafkaSourceOptions : PipelineOptions {
    @get:Description("Kafka bootstrap servers")
    @get:Required
    @get:Default.String("localhost:9092")
    var bootstrapServers: String

    @get:Description("Kafka topic to read from")
    @get:Required
    @get:Default.String("bct")
    var inputTopic: String

    @get:Description("Consumer group ID")
    @get:Required
    @get:Default.String("test-cg")
    var consumerGroup: String

    @get:Description("Auto offset reset config (earliest/latest)")
    @get:Default.String("earliest")
    var autoOffsetReset: String

    @get:Description("Log level (INFO/DEBUG/WARN/ERROR)")
    @get:Default.String("INFO")
    var logLevel: String
}

fun main(args: Array<String>) {
    val options = PipelineOptionsFactory.fromArgs(*args)
        .withValidation()
        .`as`(KafkaSourceOptions::class.java)

//
    val p = Pipeline.create(options)


    p.apply(
        "Reading from kafka",
        KafkaIO.read<String, String>()
            .withBootstrapServers(options.bootstrapServers)
            .withTopic(options.inputTopic)
            .withKeyDeserializer(StringDeserializer::class.java)
            .withValueDeserializer(StringDeserializer::class.java)
            .updateConsumerProperties(mapOf(
                "group.id" to options.consumerGroup,
                "auto.offset.reset" to options.autoOffsetReset
            ))
    ).apply("Log Kafka Messages", MapElements.via(KafkaTransform()))
    p.run().waitUntilFinish()
}


