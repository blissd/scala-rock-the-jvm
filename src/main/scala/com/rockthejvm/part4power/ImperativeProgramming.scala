package com.rockthejvm.part4power

object ImperativeProgramming {
  val meaningOfLife: Int = 42

  var aVariable = 99
  aVariable = 100 // vars can be reassigned
  aVariable += 10

  // loops
  def testLoop(): Unit = {
    var i = 0
    while (i < 10) {
      println(s"Counter at $i")
      i += 1
    }
  }

  def main(args: Array[String]): Unit = {
    testLoop()
  }

}
