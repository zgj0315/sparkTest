import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "org.after90",
  version := "0.1.0",
  scalaVersion := "2.10.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "SparkStreamingTest",
    libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.5.1",
    libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.5.1"
  )
