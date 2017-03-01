package org.after90.sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, State, StateSpec, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by zhaogj on 01/03/2017.
  */
object MapWithState {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MapWithStateDemo")
    val ssc = new StreamingContext(conf, Seconds(10))
    val zkQuorum = "localhost:2181"
    val group = "com.qzt360"
    val topics = "topic_test"
    val numThreads = 1

    ssc.checkpoint("checkpoint")

    val initialRDD = ssc.sparkContext.parallelize(List(("hello", 1), ("world", 1)))

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val stream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    val value = stream.map(_._2)

    val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
      val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
      val output = (word, sum)
      state.update(sum)
      output
    }
    val wordDstream = value.flatMap(_.split(" ")).map(word => (word, 1))
    val stateDstream = wordDstream.mapWithState(
      StateSpec.function(mappingFunc).initialState(initialRDD))

    stateDstream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
