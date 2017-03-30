package org.after90.spark.scala

import org.scalatest.FlatSpec

/**
  * Created by zhaogj on 30/03/2017.
  */
class SetSpec extends FlatSpec {
  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }
  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}
