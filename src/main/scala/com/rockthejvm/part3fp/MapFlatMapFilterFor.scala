package com.rockthejvm.part3fp

object MapFlatMapFilterFor {

  // standard list
  val aList = List(1,2,3) // [1] -> [2] -> [3] -> Nil // [1,2,3]
  val firstElement = aList.head
  val restOfElements = aList.tail

  // map
  val anIncrementedList = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  val aFlatMappedList = aList.flatMap(toPair) // [1,2,2,3,3,4]

  // All the possible combinations of all the elements of the
  // list in the format "1a - black".
  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')
  val colours = List("black", "white", "red")

  val combinations = numbers.withFilter(_ % 2 == 0).flatMap {x =>
    chars.flatMap { y =>
      colours.map(z => s"$x$y - $z")
    }
  }

  // for-comprehensions = IDENTICAL to flatMap + map chains
  val combinationsFor = for {
    number <- numbers if number %2 == 0 // generator
    char <- chars
    colour <- colours
  } yield {
    s"$number$char - $colour" // an EXPRESSION
  }

  // for-comprehensions with Unit
  // if foreach

  /**
   * Exercises
   * 1. LList support for comprehensions? Yes. But perhaps not withFilter
   * 2. A small collection of AT MOST ONE element - Maybe[A]
   *  - map
   *  - flatMap
   *  - filter
   */

  def main(args: Array[String]): Unit = {
    combinations.zip(combinationsFor).foreach(println)

    for {
      num <- numbers
    } println(num)
  }

}
