package ru.ir

import org.scalajs.dom
import org.scalajs.dom.ext._
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js

object SnakeGame extends js.JSApp {

  val BLOCK_EDGE_SIZE = 15
  val UP = 0
  val DOWN = 1
  val LEFT = 2
  val RIGHT = 3

  val CLEAN = "#FFFFFF"
  val FOOD = "#67EBAD"
  val WALL = "#292B30"
  val SNAKE = "#1D5FDB"
  val SNAKE_HEAD = "#0B157D"

  def main(): Unit = {
    val canvas = dom.document.createElement("canvas").cast[HTMLCanvasElement]
    canvas.width = 500 //(0.3 * g.window.innerWidth.cast[Int]).toInt
    canvas.height = 500 // (0.25 * g.window.innerHeight.cast[Int]).toInt
    canvas.width = canvas.width - (canvas.width % BLOCK_EDGE_SIZE)
    canvas.height = canvas.height - (canvas.height % BLOCK_EDGE_SIZE)
    //    dom.document.getElementById("playground").appendChild(canvas)

    val game = new Game(dom.document.getElementById("playground"), canvas)
  }

}
