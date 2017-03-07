package org.after90.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by zhaogj on 07/03/2017.
  */
object SparkSessionTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("spark session example")
      .getOrCreate()
    val df = spark.read.json("./data/people.json")
    df.show()
    spark.stop()
  }
}
