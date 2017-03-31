package org.after90.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by zhaogj on 31/03/2017.
  */
object YOBSpark {
  def main(args: Array[String]): Unit = {
    val strAppName = "YOBSpark"
    //方便调试，如果运行在集群上，需要将master设置为yarn-cluster
    var strMaster = "local[*]"
    if (args.length == 1) {
      strMaster = args(0)
    }
    val spark = SparkSession
      .builder()
      .master(strMaster)
      .appName(strAppName)
      .getOrCreate()
    val sc = spark.sparkContext

    val res = sc.wholeTextFiles("./data/names/*.txt", 40)
      //将
      .flatMap { case (strFilePath, strContent) => {
      val nFilePathLength = strFilePath.length
      val strYear = strFilePath.substring(nFilePathLength - 8, nFilePathLength - 4)
      val astrLine = strContent.split("\n")
      val nstrLineSize = astrLine.size
      for (i <- 0 until nstrLineSize) {
        astrLine(i) = strYear + ";" + astrLine(i)
      }
      astrLine
    }
    }
      .map { x => {
        val astrPart = x.split(";")
        (astrPart(0), 1)
      }
      }
      .reduceByKey(_ + _)
      .foreach(println)

    spark.stop()
  }


}
