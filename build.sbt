import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "org.after90",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

val spark = "org.apache.spark" % "spark-core_2.11" % "2.1.0"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "SparkTest",
    libraryDependencies += spark
  )
