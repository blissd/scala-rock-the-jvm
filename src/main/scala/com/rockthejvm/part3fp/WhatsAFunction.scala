package com.rockthejvm.part3fp

object WhatsAFunction {

  // FP: functions are "first-class" citizens
  // JVM

  trait MyFunction[A, B] {
    def apply(arg: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningOfLife = 42
  val meaningDoubles = doubler(meaningOfLife) // doubler.apply(meaningOfLife)

  // function type
  val doublerStandard = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningDoubles_v2 = doublerStandard(meaningOfLife)

  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  val anAddition = adder(2, 67)

  // (Int, String, Double, Boolean) => Int ==== Function4[Int, String, Double, Bool, Int]
  val aFourArgFunction = new Function4[Int, String, Double, Boolean, Int]{
    override def apply(v1: Int, v2: String, v3: Double, v4: Boolean): Int = ???
  }

  // all functions are instances of FunctionX with apply methods

  /**
   * Exercises
   * 1. A function which takes 2 strings and concatenates them.
   * 2. In LList, replace Predicate/Transformer with appropriate function types.
   * 3. Define a function which takes an Int as an argument and returns another Function
   * as a result.
   */

  val concat = (a: String, b: String) => a + b

  val superAdder = (a: Int) => (b: Int) => a + b
  
  // function values != methods

  def main(args: Array[String]): Unit = {

    println(concat("a", "b"))

    // currying
    val f = superAdder(1)
    println(f(2))

    val anAddition_v2 = superAdder(2)(5)

  }

}
