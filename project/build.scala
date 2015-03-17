import org.scalajs.sbtplugin.ScalaJSPlugin
import sbt._
import Keys._

object SnakeBuild extends Build {
  val Organization = "ru.ir"
  val Name = "Snake Game"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.1"
  val ScalatraVersion = "2.2.3"

  lazy val site = Project(
    "snakeSite",
    file("site"),
    settings = Defaults.coreDefaultSettings ++ Seq(
      organization := Organization,
      name := "site",
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime"
      )))

  lazy val client = Project(
    "snakeClient",
    file("client"),
    settings = Defaults.coreDefaultSettings ++ Seq(
      organization := Organization,
      name := "client",
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-dom" % "0.8.0",
        "com.lihaoyi" %% "utest" % "0.3.0" % "test"
      ))) enablePlugins ScalaJSPlugin

}