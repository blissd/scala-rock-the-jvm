package com.rockthejvm.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure {

  // Try = a potentially failed computation
  val aTry: Try[Int] = Try(42)
  val aFailedTry: Try[Int] = Try(throw new RuntimeException)

  // alt construction
  val aTry_v2: Try[Int] = Success(42)
  val aFailedTry_v2: Try[Int] = Failure(new RuntimeException)

  // main API
  val checkSuccess = aTry.isSuccess
  val checkFailure = aTry.isFailure
  val aChain = aFailedTry.orElse(aTry)

  // map, flatMap, filter, for comprehensions
  val anIncrementedTry = aTry.map(_ + 1)
  val aFlatMappedTry = aTry.flatMap(mol => Try(s"My meaning of life is $mol"))
  val aFilteredTry = aTry.filter(_ % 2 == 0)

  // WHY: avoid unsafe APIs which can THROW exceptions
  def unsafeMethod(): String =
    throw new RuntimeException("No string for you, buster!")

  // defensive: try-catch-finally
  val stringLengthDefensive = try {
    val aString = unsafeMethod()
    aString.length
  } catch {
    case e: RuntimeException => -1
  }

  // purely functional
  val stringLengthPure = Try(unsafeMethod()).map(_.length).getOrElse(-1)

  // DESIGN
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No string for you, buster!"))
  def betterBackupMethod(): Try[String] = Success("Scala")
  val stringLengthPure_v2 = betterUnsafeMethod().map(_.length)
  val aSafeChain = betterUnsafeMethod().orElse(betterBackupMethod())

  /**
   * Exercise:
   *  obtain a connection
   *  then fetch the url
   *  then print the resulting HTML
   */
  val host = "localhost"
  val port = "8081"

  class Connection {
    val random = new Random()
    def get(url: String): String = {
      if (random.nextBoolean()) {
        "<html>Success</html>"
      } else {
        throw new RuntimeException("Cannot fetch page right now")
      }
    }
  }

  object HttpService {
    val random = new Random()
    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) {
        new Connection()
      } else {
        throw new RuntimeException("Cannot access host/port combination")
      }
    }

  }

  def main(args: Array[String]): Unit = {

  }

}
