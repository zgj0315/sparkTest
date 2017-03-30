package org.after90.spark.scala

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

/**
  * Created by zhaogj on 30/03/2017.
  */
class ExampleSpec extends FlatSpec with Matchers {
  info("Starting A Stack")
  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be(2)
    stack.pop() should be(1)
    info("OK...")
  }
  info("Starting it")
  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new mutable.Stack[Int]
    a[NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
    info("OK...")
  }
}