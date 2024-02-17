package com.rockthejvm.part2oop

object Exceptions {

  val aString: String = null
  // aString.length crashes with a NPE

  // 1 - throw exception
  // val aWeirdValue: Int = throw new NullPointerException // returns Nothing

  // type Throwable
  //    Error, e.g., StackOverflowError, OutOfMemoryError
  //    Exception, e.g., NullPointerException, NoSuchElementException

  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  val potentialFail = try {
    // code that might fail
    getInt(true) // an Int
  } catch {
    // most specific exceptions first
    case e: NullPointerException => 35
    case e: RuntimeException => 54 // an Int
    // ...
  } finally {
    // executed no matter what
    // close resources
  }

  // custom exceptions
  class MyException extends RuntimeException {
    // fields or methods
    // ...
  }

  val myException = new MyException

  /**
   * Exercises:
   *
   * 1. Crash with StackOverflowError.
   * 2. Crash with OutOfMemoryError.
   * 3. Find an element matching predicate in LList.
   */

  def crashWithStackOverflowError(): Int = {
    def forever(): Int = 1 + forever()
    forever()
  }

  def crashWithOutOfMemoryError(): Unit = {
    Array.fill(Int.MaxValue)(0)
  }

  def main(args: Array[String]): Unit = {
    println(potentialFail)
    //    val throwingMyException = throw myException
    //    crashWithOutOfMemoryError()
  }
}
