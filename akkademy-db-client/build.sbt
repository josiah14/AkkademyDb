name := """akkademy-db-client"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

// Test dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.1" % "test"
)

// Internal/Local dependencies
libraryDependencies ++= Seq(
  "com.akkademy-db" %% "akkademy-db" % "1.0.0-SNAPSHOT"
)

// Akka development dependencies
// libraryDependencies ++= Seq(
//   "com.typesafe.akka" %% "akka-actor" % "2.4.1"
// )
