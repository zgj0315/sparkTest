import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "org.after90",
  version := "0.1.0",
  scalaVersion := "2.10.6"
)

unmanagedBase := baseDirectory.value / "lib_huawei"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "SparkStreamingTest"
  )
