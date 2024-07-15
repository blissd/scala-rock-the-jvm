package com.rockthejvm.part3fp

import scala.util.Random

object Options {

  // options = "collections" with a most one value
  val anOption: Option[Int] = Option(42)
  val anEmptyOption: Option[Int] = Option.empty

  val aPresentValue: Option[Int] = Some(4)
  val anEmptyOption_v2: Option[Int] = None

  // "standard" API
  val isEmpty = anOption.isEmpty
  val innerValue = anOption.getOrElse(0)
  val anotherOption = Option(46)
  val aChainedOption = anEmptyOption.orElse(anotherOption)

  // map, flatMap, filter, for
  val anIncrementedOption = anOption.map(_ + 1) // Some(43)
  val aFilteredOption = anIncrementedOption.filter(_ % 2 == 0) // None
  val flatMappedOption = anOption.flatMap(value => Option(value * 10)) // Some(420)

  // WHY options: work with unsafe API
  def unsafeMethod(): String = null
  def fallbackMethod(): String = "some valid result"

  // defensive style
  val stringLength = {
    val s = unsafeMethod()
    if (s == null) -1
    else s.length
  }

  // option-style: no need for null check
  val stringLengthOption = Option(unsafeMethod()).map(_.length)

  // use-case for orElse
  val someResult = Option(unsafeMethod()).orElse(Option(fallbackMethod()))

  // DESIGN
  def betterUnsafeMethod(): Option[String] = None
  def betterFallbackMethod(): Option[String] = Option("some valid result")
  val betterChain = betterUnsafeMethod().orElse(betterFallbackMethod())

  // example: Map.get
  val phoneBook = Map(
    "Daniel" -> 1234
  )
  val marysPhoneNumber = phoneBook.get("Mary")

  /**
   * Exercise:
   * Get the host and port from the config map.
   *  try to open a connection,
   *  print "Conn succesful"
   *  or "Conn failed"
   */
  val  config: Map[String, String] = Map(
    // comes from elsewhere
    "host" -> "176.45.32.1",
    "port" -> "8081",
  )

  class Connection {
    def connect(): String = "Connection successful"
  }

  object Connection {
    val random = new Random()
    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Option(new Connection)
      else Option.empty
    }
  }

  def main(args: Array[String]): Unit = {
    val message = for {
      host <- config.get("host")
      port <- config.get("port")
      connection <- Connection("host", "port")
    } yield connection.connect()

    println(message.getOrElse("Connection failed"))
  }
}
