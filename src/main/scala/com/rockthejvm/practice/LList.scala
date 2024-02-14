package com.rockthejvm.practice

import scala.annotation.tailrec

// singling linked list
// [1,2,3] = [1] -> [2] -> [3] -> |
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  def add(element: A): LList[A] = new Cons(element, this)

  def map[B](t: Transformer[A, B]): LList[B] = {
    def loop(remainder: LList[A]): LList[B] = {
      if (remainder.isEmpty) new Empty
      else Cons(t.transform(remainder.head), loop(remainder.tail))
    }

    loop(this)
  }

  def filter(p: Predicate[A]): LList[A] = {
    def loop(remainder: LList[A]): LList[A] = {
      if (remainder.isEmpty) remainder
      else if (p.test(remainder.head)) {
        val rest = loop(remainder.tail)
        Cons(remainder.head, rest)
      }
      else loop(remainder.tail)
    }
    loop(this)
  }

  def flatMap[B](f: Transformer[A, LList[B]]): LList[B] = {
    
    def concat(left: LList[B], right: LList[B]): LList[B] = {
      if (left.isEmpty) right
      else if (left.tail.isEmpty) new Cons(left.head, right)
      else {
        val rest = concat(left.tail, right)
        new Cons(left.head, rest)
      }
    }

    def loop(remainder: LList[A]): LList[B] = {
      if (remainder.isEmpty) {
        new Empty[B]
      } else {
        val rest = loop(remainder.tail)
        val front = f.transform(remainder.head)
        concat(front, rest)
      }
    }

    loop(this)
  }
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

/*
Exercise: LList extension.

1. Generic trait Predicate[T] wiht a little test(T) => Boolean.
2. Generic trait Transformer[A, B] with a method named transform(A) => B
3. LList
 - map(transformer) => LList
 - filter(predicate) => LList
 - flatMap(transformer from A to LList[B]) => LList[B]

class EvenPredicate extends Predicate[Int]
class StringToIntTransformer extends Transformer[String, Int]

[1,2,3].map(n * 2) = [2,4,6]
[1,2,3,4].filter(n % 2) = [2,4]
[1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
 */

trait Predicate[T] {
  def test(v: T): Boolean
}

object EvenPredicate extends Predicate[Int] {

  override def test(v: Int): Boolean = v % 2 == 0
}

object StringToIntTransformer extends Transformer[String, Int] {

  override def transform(a: String): Int = a.toInt
}

trait Transformer[A, B] {
  def transform(a: A): B
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

    /*

[1,2,3].map(n * 2) = [2,4,6]
[1,2,3,4].filter(n % 2) = [2,4]
[1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
     */
    val mappedList = first3Numbers.map((a: Int) => a * 2)
    println(s"map = $mappedList")

    val first4Numbers = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Empty))))
    val filteredList = first4Numbers.filter(EvenPredicate)
    println(s"filter = $filteredList")

    val flatMappedList = first3Numbers.flatMap((a: Int) => Cons(a, Cons(a + 1, new Empty)))
    println(s"flatMap = $flatMappedList")

  }
}