package org.after90.sparkStreaming


import com.huawei.hadoop.security.LoginUtil
import kafka.serializer.StringDecoder
import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ReadKafka {

  def main(args: Array[String]): Unit = {
    val userPrincipal = "admin"
    val userKeytabPath = "/Users/zhaogj/github/zgj0315/sparkTest/config/user.keytab"
    val krb5ConfPath = "/Users/zhaogj/github/zgj0315/sparkTest/config/krb5.conf"

    val hadoopConf: Configuration = new Configuration();
    println("before login")
    LoginUtil.login(userPrincipal, userKeytabPath, krb5ConfPath, hadoopConf)
    println("after login")
    val topics = "topic_message_spark"
    val brokers = "172.16.36.202:21007,172.16.36.201:21007,172.16.36.200:21007"
//    val brokers = "172.16.12.104:9092"

    // Create a Streaming startup environment.
    val sparkConf = new SparkConf().setAppName("DataSightStreamingExample").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    // Configure the CheckPoint directory for the Streaming. This parameter is mandatory because of existence of the window concept.
    ssc.checkpoint("checkpoint")

    // Get the list of topic used by kafka
    val topicsSet = topics.split(",").toSet

    // Create direct kafka stream with brokers and topics
    // Receive data from the Kafka and generate the corresponding DStream
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet) //.map(_._2)

    lines.foreachRDD(rdd => {
      rdd.foreachPartition(f => {
        while (f.hasNext) {
          print(f.next())
        }
      })
    })
    lines.print()

    // start Streaming
    ssc.start()
    ssc.awaitTermination()


  }
}
