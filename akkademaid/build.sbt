name := """akkademaid"""

version := "1.0"

scalaVersion := "2.11.7"

// Test dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.1" % "test"
)

// Local dependencies
libraryDependencies ++= Seq(
  "com.akkademy-db" %% "akkademy-db" % "1.0.0-SNAPSHOT",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "2.0.3",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.0.3"
)

// boilerpipe dependencies
libraryDependencies ++= Seq(
  "com.syncthemall" % "boilerpipe" % "1.2.2"
)
// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

