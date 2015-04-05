enablePlugins(ScalaJSPlugin)

name := "Snake Game ScalaJS"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

persistLauncher in Compile := true

persistLauncher in Test := false

testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "com.lihaoyi" %%% "scalatags" % "0.5.1",
  "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)
