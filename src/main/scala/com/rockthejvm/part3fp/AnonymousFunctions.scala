package com.rockthejvm.part3fp

object AnonymousFunctions {

  // instances of FunctionN
  val doubler: Int => Int = new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }

  // lambdas = anonymous function instances
  val doubler_v2: Int => Int = (x: Int) => x * 2 // Identical
  val adder: (Int, Int) => Int = (x: Int, y: Int) => x + y // new Function2[Int, Int, Int]...

  // zero-arg functions
  val justDoSomething: () => Int = () => 45
  val anInvocation = justDoSomething()

  // alt syntax with curly braces
  val stringToInt = { (str: String) =>
    // implementation: code block
    str.toInt
  }

  // type inference
  val doubler_v3: Int => Int = x => x * 2 // type of x inferred by compiler
  val adder_v2: (Int, Int) => Int = (x, y) => x + y // types of x and y inferred

  // shortest lambdas
  val doubler_v4: Int => Int = _ * 2 // x => x * 2
  val adder_v3: (Int, Int) => Int = _ + _ // (x, y) => x + y
  // each underscore is a different argument, you can't reuse them

  /**
   * Exercises:
   * 1. In LList, replace FunctionN instantiations with Lambdas.
   * 2. Rewrite superAdder in WhatIsAScala.scala using lambdas.
   * @param args
   */

  def main(args: Array[String]): Unit = {
    println(justDoSomething)
    println(justDoSomething())

  }

}
