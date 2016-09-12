scalaVersion := "2.11.8"

name := "play2-mail-plugin"

organization := "pl.finpack"

version := "2.2.2"

resolvers ++= Seq(
    "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:reflectiveCalls"
)

libraryDependencies ++= Seq(
  "org.codemonkey.simplejavamail" % "simple-java-mail" % "2.0",
  "com.typesafe.play" %% "play" % "2.5.4",
  "com.typesafe.play" %% "play-test" % "2.5.4",
  "org.specs2" %% "specs2" % "3.7" % "test",
  "junit" % "junit" % "4.8" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.9" % "test"
)

