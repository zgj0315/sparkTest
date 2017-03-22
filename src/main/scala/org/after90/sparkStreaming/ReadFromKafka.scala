package org.after90.sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by zhaogj on 28/02/2017.
  */
object ReadFromKafka {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("ReadFromKafka")
    val ssc = new StreamingContext(conf, Seconds(10))
    val zkQuorum = "localhost:2181"
    val group = "org.after90"
    val topics = "topic_test"
    val numThreads = 1

    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val stream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    stream.print()
    //val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    //lines.print()
    //val words = lines.flatMap(_.split(" "))
    //words.print()
    //    val wordCounts = words.map(x => (x, 1L))
    //      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(2), Seconds(2), 2)
    //    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
