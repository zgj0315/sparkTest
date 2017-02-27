package org.after90.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._
/**
  * Created by zhaogj on 27/02/2017.
  */
object ReadFromES {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ReadFromES").setMaster("local[*]")
    conf.set("es.nodes", "http://localhost:9200")
    val sc = new SparkContext(conf)
    val RDD = sc.esRDD("spark/docs")
    println(RDD.count)
    }
}
