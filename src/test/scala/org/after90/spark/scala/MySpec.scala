package org.after90.spark.scala

import scala.collection.mutable

/**
  * Created by zhaogj on 30/03/2017.
  */
class MySpec extends UnitSpec {
  "My Test" should "It can work" in {
    val left = 2
    val right = 1
    //assert(left == right)
    assertResult(1) {
      left - right
    }
  }

}
