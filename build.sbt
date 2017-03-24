import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "org.after90",
  name := "SparkTest",
  version := "0.1.0",
  scalaVersion := "2.10.6"
)

resolvers ++= Seq(
  "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
)

val spark = "org.apache.spark" % "spark-core_2.10" % "1.3.0-cdh5.4.5"
val mllib = "org.apache.spark" % "spark-mllib_2.10" % "1.3.0-cdh5.4.5" excludeAll (ExclusionRule(organization = "javax.servlet"))
val hdfs = "org.apache.hadoop" % "hadoop-hdfs" % "2.6.0-cdh5.4.5" excludeAll (ExclusionRule(organization = "javax.servlet"))
val spark_streaming_kafka = "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.3.0-cdh5.4.5" excludeAll (ExclusionRule(organization = "javax.servlet"))

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    //以下注释掉的部分是研究如何使用jdk1.7编译程序，没搞明白，暂且搁置
    // append several options to the list of options passed to the Java compiler
    //javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
    //scalacOptions += "-target:jvm-1.7",

    // fork a new JVM for 'run' and 'test:run'
    //fork := true,

    // fork a new JVM for 'test:run', but not 'run'
    //fork in Test := true,

    // add a JVM option to use when forking a JVM for 'run'
    //javaOptions += "-Xmx2G",

    // set the location of the JDK to use for compiling Java code.
    // if 'fork' is true, this is used for 'run' as well
    //javaHome := Some(file("/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home")),

    libraryDependencies += spark,
    libraryDependencies += mllib,
    libraryDependencies += hdfs,
    libraryDependencies += spark_streaming_kafka
  )
