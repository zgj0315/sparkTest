package org.after90.spark.scala

import org.scalatest.FlatSpec

import scala.collection.mutable.ListBuffer

/**
  * Created by zhaogj on 30/03/2017.
  */
class FixtureSpec extends FlatSpec {
  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }

  "Testing" should "be easy" in {
    val f = fixture
    f.builder.append("easy!")
    assert(f.builder.toString === "ScalaTest is easy!")
    assert(f.buffer.isEmpty)
    f.buffer += "sweet"
  }

  info("start it")
  it should "be fun" in {
    val f = fixture
    f.builder.append("fun!")
    info("fun")
    assert(f.builder.toString === "ScalaTest is fun!")
    info("empty")
    assert(f.buffer.isEmpty)
  }
}
