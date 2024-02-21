package com.rockthejvm.part2oop

// Since Scala 3 you can define fields and methods outside of object.
// Not recommended.
val meaningOfLife = 42
def computeMyLife: String = "Scala"

object PackagesImports { // top-level

  // packages = "folders"

  // fully qualified name
  val aList: com.rockthejvm.practice.LList[Int] = ???
  
  // import
  import com.rockthejvm.practice.LList
  val anotherList: LList[Int] = ???
  
  // importing under an alias
  import java.util.List as JList
  val aJavaList: JList[Int] = ???
  
  // import everything
  import com.rockthejvm.practice.*
  val aPredicate: Cons[Int] = ???
  
  // import multiple symbols
  import PhysicsConstants.{SPEED_OF_LIGHT, EARTH_GRAVITY}
  val c = SPEED_OF_LIGHT
  
  // import everything BUT something
  object PlayingPhysics {
    import PhysicsConstants.{PLANK as _, *}
//    val plank = PLANK // won't compile
  }
  
  import com.rockthejvm.part2oop.* // import meaningOfLife and computeMyLife
  val mol = meaningOfLife
  
  // default imports
  // scala.*, scala.Predef.*, java.lang.*
  
  // exports - since Scala 3
  class PhysicsCalculator {
    import PhysicsConstants.*
    def computePhotonEnergy(wavelength: Double): Double =
      PLANK / wavelength
  }
  
  object ScienceApp {
    val physicsCalculator = new PhysicsCalculator
    
    export physicsCalculator.computePhotonEnergy
    
    def computeEquivalentMass(wavelength: Double): Double =
      computePhotonEnergy(wavelength) / (SPEED_OF_LIGHT * SPEED_OF_LIGHT)
  }

}

object PhysicsConstants {
  // constants
  val SPEED_OF_LIGHT = 299792458
  val PLANK = 6.62e-34 // scientific notation
  val EARTH_GRAVITY = 9.8
}