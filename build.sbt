import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "org.after90",
  version := "0.1.0",
  scalaVersion := "2.10.6"
)

//val spark_core = "org.apache.spark" % "spark-core_2.10" % "1.5.1"
//val spark_streaming = "org.apache.spark" % "spark-streaming_2.10" % "1.5.1"
//val spark_streaming_kafka = "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.5.1"
//unmanagedBase := baseDirectory.value / "lib"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "SparkStreamingTest"
//    libraryDependencies += spark_core,
//    libraryDependencies += spark_streaming,
//    libraryDependencies += spark_streaming_kafka
  )
