package com.rockthejvm.practice

import scala.annotation.tailrec
import scala.collection.immutable.Set

/**
 * Social network = Map[String, Set[String]]
 * Friend relationships are MUTUAL.
 *
 * - add a person to the network
 * - remove a person from the network
 * - add a friend relationship (network, a, b)
 * - unfriend
 *
 * - number of friends of a person
 * - who has the most friends
 * - how many people have NO friends
 * + if there is a social connection between two people (direct or not)
 *
 * Daniel <-> Mary <-> Jane <-> Tom
 */

object TuplesMapsExercises {

  def addPerson(network: Map[String, Set[String]], newPerson: String): Map[String, Set[String]] =
    network + (newPerson -> Set[String]())

  def removePerson(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    if (!network.contains(person)) network
    else network.view
      .mapValues(friends => friends - person) // remove person from all friend groups
      .filterKeys(_ == person) // remove person from network
      .toMap

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    if (!network.contains(a)) throw new IllegalArgumentException(s"$a not in network")
    if (!network.contains(b)) throw new IllegalArgumentException(s"$b not in network")
    network.updated(a, network(a) + b).updated(b, network(b) + a)
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    if (!network.contains(a) || !network.contains(b)) network
    else network.updated(a, network(a) - b).updated(b, network(b) - a)
  }

  def countFriends(network: Map[String, Set[String]], person: String): Int =
    if (network.contains(person)) network(person).size
    else 0

  def mostPopular(network: Map[String, Set[String]]): String =
    if (network.isEmpty) throw new IllegalStateException("Network is empty")
    else {
      network.maxBy(_._2.size)._1
    }

  def countFriendless(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {

    @tailrec
    def search(visited: Set[String], unvisited: Set[String]): Boolean = {
      if (unvisited.isEmpty) false
      else if (unvisited.contains(b)) true
      else {
        val person = unvisited.head
        search(visited + person, unvisited ++ network(person) -- visited - person)
      }
    }

    if (!network.contains(a) || !network.contains(b)) false
    else search(Set(), network(a))
  }

  def main(args: Array[String]): Unit = {
    val empty: Map[String, Set[String]] = Map()
    val network = addPerson(addPerson(empty, "Bob"), "Mary")
    println(network)
    println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))

    val people = addPerson(addPerson(addPerson(empty, "Bob"), "Mary"), "Jim")
    val simpleNet = friend(friend(people, "Bob", "Mary"), "Jim", "Mary")
    println(simpleNet)
    println(countFriends(simpleNet, "Mary"))
    println(countFriends(simpleNet, "Bob"))
    println(countFriends(simpleNet, "Daniel"))

    println(mostPopular(simpleNet))

    println(countFriendless(addPerson(simpleNet, "Daniel")))

    println(socialConnection(simpleNet, "Bob", "Jim"))
    println(socialConnection(addPerson(simpleNet, "Daniel"), "Bob", "Daniel"))
  }

}
