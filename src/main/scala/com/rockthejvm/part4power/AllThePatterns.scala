package com.rockthejvm.part4power
import com.rockthejvm.practice.*

object AllThePatterns {

  object MySingleton

  // 1 - constants
  val someValue: Any = "Scala"
  val constants = someValue match {
    case 42 => "a number"
    case "Scala" => "THE Scala"
    case true => "the truth"
    case MySingleton => "a singleton object"
  }

  // 2 - match anything
  val matchAnythingVar = 2 + 3 match {
    case something => s"I've matched anything, it's $something"
  }
  val matchAnything = someValue match {
    case _ => "I can match anything at all"
  }

  // 3 - tuples
  val aTuple = (1, 4)
  val matchTuple = aTuple match {
    case (1, somethingElse) => "A tuple with 1 and $somethingElse"
    case (something, 2) => "A tuple with 2 as its second field"
  }

  // PM structures can be NESTED
  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) => "A nexted tuple"
  }

  // 4 - case classes
  val aList: LList[Int] = Cons(1, Cons(2, Empty()))
  val matchList = aList match {
    case Empty() => "an empty list"
    case Cons(head, Cons(_, tail)) => s"a non-empty list starting with $head"
  }

  val anOption: Option[Int] = Option(2)
  val matchOption = anOption match {
    case Some(n) => s"an option with $n"
    case None => "an empty option"
  }

  // 5 - list patterns
  val aStandardList = List(1,2,3,42)
  val matchStandardList = aStandardList match {
    case List(1,_, _, _) => s"a four element list starting with 1"
    case List(1, _*) => "list starting with 1"
    case List(1, 2, _) :+ 42 => "list ending with 42"
    case head :: tail => "deconstructed list"
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val matchType = unknown match {
    case anInt: Int => s"an int: $anInt"
    case aString: String => "I matched a String"
    case _: Double => "I matched a double I don't care about"
  }

  // 7 - name binding
  val bindNames = aList match {
    case Cons(head, rest @ Cons(_, tail)) => s"Can use $rest"
  }

  // 8 - chained patterns
  val multiMatch = aList match {
    case Empty() | Cons(0, _) => "an empty list to me"
    case _ => "something else"
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(special, _)) if special > 5 => "second element is big enough"
    case _ => "anything else"
  }

  /*
  Example: does this make sense?
   */
  val aSimpleInt = 45
  val isEven_bad = aSimpleInt match {
    case n if n % 2 == 0 => true
    case _ => false
  }

// heavy anti-pattern
val isEven_bad_v2 = if (aSimpleInt % 2 == 0) true else false

// better
val isEven = aSimpleInt % 2 == 0

/*
Exercise (trick)
 */
val numbers: List[Int] = List(1,2,3,4)
val numbersMatch = numbers match {
  case listOfString: List[String] => "a list of strings"
  case listOfInts: List[Int] => "a list of numbers"
}

/*
PM runs at runtime
- reflection
- generic tyhpes are erased at runtime
List[String] => List
List[Int] => List
Function1[Int, String] => Function1
 */

  def main(args: Array[String]): Unit = {
    println(numbersMatch)
  }
}
