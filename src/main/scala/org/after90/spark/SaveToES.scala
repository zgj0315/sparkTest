package org.after90.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._

/**
  * Created by zhaogj on 27/02/2017.
  */
object SaveToES {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SaveToES").setMaster("local[*]")
    conf.set("es.nodes", "http://localhost:9200")
    conf.set("es.net.http.auth.user", "elastic")
    conf.set("es.net.http.auth.pass", "changeme")

    val sc = new SparkContext(conf)
    val numbers = Map("one" -> 1, "two" -> 2, "three" -> 3)
    val airports = Map("arrival" -> "Otopeni", "SFO" -> "San Fran")
    sc.makeRDD(Seq(numbers, airports)).saveToEs("spark/docs")
  }
}
