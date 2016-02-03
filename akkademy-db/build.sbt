name := """akkademy-db"""

organization := """com.akkademy-db"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

mappings in (Compile, packageBin) ~= { _.filterNot { case (_, name) =>
  Seq("application.conf").contains(name)
}}

// Test dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.1" % "test"
)

// Akka development dependencies
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.1",
  "com.typesafe.akka" %% "akka-remote" % "2.4.1",
  "com.typesafe.akka" %% "akka-agent" % "2.4.1"
)
