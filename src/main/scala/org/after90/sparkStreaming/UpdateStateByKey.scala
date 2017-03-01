package org.after90.sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by zhaogj on 01/03/2017.
  *
  * H     H
  * H     H
  * H     H
  * H H H H
  * H     H
  * H     H
  * H     H   H   H   H
  *
  */
object UpdateStateByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UpdateStateByKeyDemo")
    val ssc = new StreamingContext(conf, Seconds(10))
    val zkQuorum = "localhost:2181"
    val group = "com.qzt360"
    val topics = "topic_test"
    val numThreads = 1

    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val stream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    val value = stream.map(_._2)
    val addFunc = (currValues: Seq[Int], prevValueState: Option[Int]) => {
      //通过Spark内部的reduceByKey按key规约，然后这里传入某key当前批次的Seq/List,再计算当前批次的总和
      val currentCount = currValues.sum
      // 已累加的值
      val previousCount = prevValueState.getOrElse(0)
      // 返回累加后的结果，是一个Option[Int]类型
      Some(currentCount + previousCount)
    }
    value.flatMap(_.split(" ")).map(word => (word, 1))
      .updateStateByKey(addFunc).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
