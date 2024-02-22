package com.rockthejvm.practice

import scala.annotation.tailrec

// singling linked list
// [1,2,3] = [1] -> [2] -> [3] -> |
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  def add(element: A): LList[A] = Cons(element, this)

  def concat(other: LList[A]): LList[A]

  def map[B](t: A => B): LList[B]
  def filter(p: A => Boolean): LList[A]
  def flatMap[B](f: A => LList[B]): LList[B]
}

case class Cons[A](head: A, tail: LList[A]) extends LList[A] {

  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def concatenateElements(remainder: LList[A], acc: String): String = {
      if (remainder.isEmpty) acc
      else concatenateElements(remainder.tail, s"$acc, ${remainder.head}")
    }
    s"[${concatenateElements(this.tail, s"$head")}]"
  }


  def concat(other: LList[A]): LList[A] = Cons(head, tail.concat(other))

  def map[B](t: A => B): LList[B] = Cons(t(head), tail.map(t))

  def filter(p: A => Boolean): LList[A] = {
    if (p(head)) Cons(head, tail.filter(p))
    else tail.filter(p)
  }

  def flatMap[B](f: A => LList[B]): LList[B] =
    f(head).concat(tail.flatMap(f))

}

case class Empty[A]() extends LList[A] {

  override def head: A = throw new NoSuchElementException()

  override def tail: LList[A] = this

  override def isEmpty: Boolean = true
  override def toString: String = "[]"

  def concat(other: LList[A]): LList[A] = other
  def map[B](t: A => B): LList[B] = Empty()
  def filter(p: A => Boolean): LList[A] = this
  def flatMap[B](f: A => LList[B]): LList[B] = Empty()
}

/*
Exercise: LList extension.

1. Generic trait Predicate[T] with a little test(T) => Boolean.
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

object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = Empty[Int]()
    println(empty)
    println(empty.isEmpty)

    val first3Numbers = Cons(1, Cons(2, Cons(3, empty)))
    println(first3Numbers)

    val first3Numbers_v2 = empty.add(1).add(2).add(3)
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)

    val someStrings = Cons("dog", Cons("cat", Empty()))
    println(someStrings)

    val first4Numbers = Cons(1, Cons(2, Cons(3, Cons(4, Empty()))))

    /*

[1,2,3].map(n * 2) = [2,4,6]
[1,2,3,4].filter(n % 2) = [2,4]
[1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
     */

    // map testing
    val numbersDoubled = first3Numbers.map(_ * 2)
    println(s"numbersDoubled = $numbersDoubled")

    val numbersNested = first3Numbers.map(a => Cons(a, Cons(a + 1, Empty())))
    println(s"numbersNested = $numbersNested")

    // filter testing
    val onlyEvenNumbers = first3Numbers.filter(_ % 2 == 0)
    println(s"onlyEvenNumbers = $onlyEvenNumbers")

    // flatMap testing
    val flattenedList = first3Numbers.flatMap(a => Cons(a, Cons(a + 1, Empty())))
    println(s"flattenedList = $flattenedList")

    // find test
    @tailrec
    def find[A](list: LList[A], predicate: A => Boolean): A =
      if (list.isEmpty) throw new NoSuchElementException
      else if (predicate(list.head)) list.head
      else find(list.tail, predicate)

    println(find(first3Numbers, _ % 2 == 0))
//    println(find(first3Numbers, (element: Int) => element > 5)) // NoSuchElementException
  }
}