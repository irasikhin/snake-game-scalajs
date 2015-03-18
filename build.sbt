lazy val commonSettings = Seq(
  scalaVersion := "2.11.6",
  organization := "ru.ir",
  version := "0.1.0-SNAPSHOT",
  resolvers ++= Seq(
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Akka Repo" at "http://akka.io/repository/",
    "Web plugin repo" at "http://siasia.github.com/maven2",
    "bintray/non" at "http://dl.bintray.com/non/maven"
  ))

name := "Example"

val ScalatraVersion = "2.3.0"

persistLauncher in Compile := true

persistLauncher in Test := false

testFrameworks += new TestFramework("utest.runner.Framework")

lazy val site = (project in file("site")).
  settings(Defaults.coreDefaultSettings: _*).
  settings(commonSettings: _*).
  settings(
    name := "site",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "com.lihaoyi" %% "scalatags" % "0.4.6",
      "com.lihaoyi" %% "upickle" % "0.2.8",
      "com.lihaoyi" %% "autowire" % "0.2.5",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime"))

lazy val client = (project in file("client")).
  settings(Defaults.coreDefaultSettings: _*).
  settings(commonSettings: _*).
  enablePlugins(ScalaJSPlugin).
  settings(
    name := "client",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.8.0",
      "com.lihaoyi" %%% "autowire" % "0.2.5",
      "com.lihaoyi" %%% "upickle" % "0.2.8",
      "com.lihaoyi" %% "utest" % "0.3.0" % "test"
    ))
