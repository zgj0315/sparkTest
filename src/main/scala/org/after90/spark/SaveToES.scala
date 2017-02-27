package org.after90.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._
import org.elasticsearch.spark.rdd.EsSpark

/**
  * Created by zhaogj on 27/02/2017.
  */
object SaveToES {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SaveToES").setMaster("local[*]")
    conf.set("es.nodes", "http://localhost:9200")
    conf.set("es.net.http.auth.user", "elastic")
    conf.set("es.net.http.auth.pass", "changeme")
    conf.set("es.index.auto.create", "true")
    val sc = new SparkContext(conf)
    val numbers = Map("one" -> 1, "two" -> 2, "three" -> 3)
    val airports = Map("arrival" -> "Otopeni", "SFO" -> "San Fran")
    sc.makeRDD(Seq(numbers, airports)).saveToEs("spark/docs")

    // define a case class
    case class Trip(departure: String, arrival: String)

    val upcomingTrip = Trip("OTP", "SFO")
    val lastWeekTrip = Trip("MUC", "OTP")

    val rdd = sc.makeRDD(Seq(upcomingTrip, lastWeekTrip))
    EsSpark.saveToEs(rdd, "spark/docs")


    val json1 = """{"reason" : "business", "airport" : "SFO"}"""
    val json2 = """{"participants" : 5, "airport" : "OTP"}"""

    sc.makeRDD(Seq(json1, json2))
      .saveJsonToEs("spark/json-trips")


    val game = Map("media_type"->"game","title" -> "FF VI","year" -> "1994")
    val book = Map("media_type" -> "book","title" -> "Harry Potter","year" -> "2010")
    val cd = Map("media_type" -> "music","title" -> "Surfing With The Alien")

    sc.makeRDD(Seq(game, book, cd)).saveToEs("my-collection/{media_type}")

    val otp = Map("iata" -> "OTP", "name" -> "Otopeni")
    val muc = Map("iata" -> "MUC", "name" -> "Munich")
    val sfo = Map("iata" -> "SFO", "name" -> "San Fran")

    val airportsRDD = sc.makeRDD(Seq((1, otp), (2, muc), (3, sfo)))
    airportsRDD.saveToEsWithMeta("airports/2015")

  }
}
