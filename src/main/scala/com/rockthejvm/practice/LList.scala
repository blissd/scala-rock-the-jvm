package com.rockthejvm.practice

import scala.annotation.tailrec

// singling linked list
// [1,2,3] = [1] -> [2] -> [3] -> |
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  def add(element: A): LList[A] = new Cons(element, this)

}

class Cons[A](override val head: A, override val tail: LList[A]) extends LList[A] {

  override def isEmpty: Boolean = false
  override def toString: String = {
    @tailrec
    def concatenateElements(remainder: LList[A], acc: String): String = {
      if (remainder.isEmpty) acc
      else concatenateElements(remainder.tail, s"$acc, ${remainder.head}")
    }
    s"[${concatenateElements(this.tail, s"$head")}]"
  }
}

class Empty[A] extends LList[A] {

  override def head: A = throw new NoSuchElementException()

  override def tail: LList[A] = this

  override def isEmpty: Boolean = true
  override def toString: String = "[]"
}

object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = new Empty[Int]
    println(empty)
    println(empty.isEmpty)

    val first3Numbers = new Cons(1, new Cons(2, new Cons(3, empty)))
    println(first3Numbers)

    val first3Numbers_v2 = empty.add(1).add(2).add(3)
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)

    val someStrings = new Cons("dog", new Cons("cat", new Empty))
    println(someStrings)

  }
}