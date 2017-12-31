package org.after90.spark.sql

import org.apache.spark.sql.SparkSession

object EventSafeMaker {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("EventSafeMaker")
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._
    val df = List(
      ("012229", 1, 1),
      ("012229", 2, 2),
      ("012229", 3, 1),
      ("012229", 4, 1),
      ("012229", 8, 1),
      ("012229", 9, 1),
      ("012229", 10, 1),
      ("012230", 1, 1),
      ("012230", 2, 1)
    ).toDF("udid", "time", "count")
    import org.apache.spark.sql.expressions.Window
    import org.apache.spark.sql.functions._
    val wSpec = Window.partitionBy("udid")
      .orderBy("time")
      //.rowsBetween(-1, 1)
      .rangeBetween(-1, 1) //三天

    df.withColumn("movingCount",
      sum(df("count")).over(wSpec)).show()

    spark.close()

  }
}
