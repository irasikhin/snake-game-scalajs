import org.scalajs.sbtplugin.ScalaJSPlugin
import sbt._
import Keys._

object SnakeBuild extends Build {
  val Organization = "ru.ir"
  val Name = "Snake Game"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.4"
  val ScalatraVersion = "2.2.3"

  lazy val site = Project(
    "site",
    file("site"),
    settings = Defaults.coreDefaultSettings ++ Seq(
      organization := Organization,
      name := "site",
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "amateras-repo" at "http://amateras.sourceforge.jp/mvn/",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-commands" % ScalatraVersion,
        "jp.sf.amateras" %% "scalatra-forms" % "0.0.14",
        "org.scalatra" %% "scalatra-auth" % "2.2.2",
        "org.scalatra" %% "scalatra-json" % "2.2.2",
        "org.json4s" %% "json4s-jackson" % "3.2.8",
        "com.github.nscala-time" %% "nscala-time" % "0.8.0",
        "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "com.github.aselab" %% "scala-activerecord" % "0.2.3",
        "org.slf4j" % "slf4j-nop" % "1.7.5",
        "com.h2database" % "h2" % "1.3.173",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.apache.commons" % "commons-email" % "1.3.2",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "jp.sf.amateras" %% "scalatra-forms" % "0.0.14",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts Artifact("javax.servlet", "jar", "jar")
      ))) dependsOn shared aggregate shared

  lazy val client = Project(
    "client",
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

  lazy val shared = Project(
    "shared",
    file("shared"),
    settings = Defaults.coreDefaultSettings ++ Seq(
      organization := Organization,
      name := "shared",
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
      )))
}