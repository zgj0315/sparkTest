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
      ("012229", "2017-01-01"),
      ("012229", "2017-01-02"),
      ("012229", "2017-01-03"),
      ("012229", "2017-01-04"),
      ("012230", "2017-01-01"),
      ("012230", "2017-01-02"),
      ("012231", "2017-01-01")
    ).toDF("udid", "date")
    import org.apache.spark.sql.expressions.Window
    import org.apache.spark.sql.functions._
    val wSpec = Window.partitionBy("udid")
      .orderBy("date")
      .rowsBetween(-1, 1)//三天

    df.withColumn("movingCount",
      count(df("*")).over(wSpec)).show()

    spark.close()

  }
}
