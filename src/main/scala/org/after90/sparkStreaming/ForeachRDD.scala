package org.after90.sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by zhaogj on 01/03/2017.
  */
object ForeachRDD {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MapWithStateDemo")
    val ssc = new StreamingContext(conf, Seconds(10))
    val zkQuorum = "localhost:2181"
    val group = "com.qzt360"
    val topics = "topic_test"
    val numThreads = 1

    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val stream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    stream.foreachRDD { rdd => {
      val data = rdd.map { case (k, v) => (v, k) }
      data.foreach {
        case (key, value) =>
          println("key:" + key + " value:" + value)
      }
    }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
