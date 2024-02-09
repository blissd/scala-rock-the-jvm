package com.rockthejvm.practice

import scala.annotation.tailrec

// singling linked list
// [1,2,3] = [1] -> [2] -> [3] -> |
abstract class LList {
  def head: Int
  def tail: LList
  def isEmpty: Boolean
  def add(element: Int): LList = new Cons(element, this)

}

class Cons(override val head: Int, override val tail: LList) extends LList {

  override def isEmpty: Boolean = false
  override def toString: String = {
    @tailrec
    def concatenateElements(remainder: LList, acc: String): String = {
      if (remainder.isEmpty) acc
      else concatenateElements(remainder.tail, s"$acc, ${remainder.head}")
    }
    s"[${concatenateElements(this.tail, s"$head")}]"
  }
}

object Empty extends LList {

  override def head: Int = throw new NoSuchElementException()

  override def tail: LList = Empty

  override def isEmpty: Boolean = true
  override def toString: String = "[]"
}

object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = Empty
    println(empty)
    println(empty.isEmpty)

    val first3Numbers = new Cons(1, new Cons(2, new Cons(3, Empty)))
    println(first3Numbers)

    val first3Numbers_v2 = Empty.add(1).add(2).add(3)
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)

  }
}