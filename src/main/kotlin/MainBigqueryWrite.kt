package org.example

/**
 * This Kotlin program demonstrates how to create an Apache Beam pipeline to write data to a Google BigQuery table.
 *
 * Key Features:
 * 1. **GCP Configuration**:
 *    - Configures the GCS temporary bucket location for staging data.
 *    - Sets up the GCP project ID, BigQuery dataset ID, and table name.
 *
 * 2. **Pipeline Setup**:
 *    - Defines pipeline options with GCS temporary storage and GCP project information.
 *    - Creates a `Pipeline` instance to process and transfer data.
 *
 * 3. **Data Transformation**:
 *    - Converts sample user data into BigQuery-compatible `TableRow` objects.
 *
 * 4. **BigQuery Integration**:
 *    - Specifies the BigQuery table schema (fields and types).
 *    - Writes the data to the BigQuery table with the following configurations:
 *        - `WRITE_TRUNCATE`: Overwrites the existing table data if it exists.
 *        - `CREATE_IF_NEEDED`: Creates the table if it does not exist.
 *
 * 5. **Execution**:
 *    - Runs the pipeline and waits for it to complete.
 *
 * Pre-requisites:
 * - Apache Beam SDK for Java.
 * - Google Cloud SDK configured with appropriate permissions.
 * - BigQuery and GCS set up in your GCP project.
 */

import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.options.PipelineOptionsFactory
import org.apache.beam.sdk.transforms.Create
import com.google.api.services.bigquery.model.TableRow
import org.apache.beam.sdk.extensions.gcp.options.GcsOptions
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO

fun main() {
    // confgiure the GCP project and BigQuery table
    val tempBUcketLocation= "gs://<gcs_bucket_name>/tmp"
    val gcpProjectId = "gcp_project_id"
    val dataSetId = "bigquery_dataset_id"
    val tableName = "bigquery_table_name"

    // Define your pipeline options
    val options = PipelineOptionsFactory.create().`as`(GcsOptions::class.java)
    options.tempLocation= tempBUcketLocation
    options.project = gcpProjectId
    val pipeline = Pipeline.create(options)

    // Sample data
    val userData = listOf(
        mapOf("id" to 1, "name" to "Alice", "age" to 25),
        mapOf("id" to 2, "name" to "Bob", "age" to 30),
        mapOf("id" to 3, "name" to "Charlie", "age" to 35)
    )

    // Convert data to TableRow (BigQuery format)
    val rows = userData.map { data ->
        TableRow().apply {
            set("id", data["id"])
            set("name", data["name"])
            set("age", data["age"])
        }
    }

    // Define your BigQuery table
    val tableSpec = "$gcpProjectId:$dataSetId.$tableName"

    // Build the pipeline
    pipeline.apply("Create Input Data", Create.of(rows))
        .apply("Write to BigQuery",
            BigQueryIO.writeTableRows()
                .to(tableSpec)
                .withSchema(getTableSchema())
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
        )

    // Run the pipeline
    pipeline.run().waitUntilFinish()
}

// Define the schema for your BigQuery table
fun getTableSchema(): com.google.api.services.bigquery.model.TableSchema {
    return com.google.api.services.bigquery.model.TableSchema().apply {
        fields = listOf(
            com.google.api.services.bigquery.model.TableFieldSchema().setName("id").setType("INTEGER"),
            com.google.api.services.bigquery.model.TableFieldSchema().setName("name").setType("STRING"),
            com.google.api.services.bigquery.model.TableFieldSchema().setName("age").setType("INTEGER")
        )
    }
}