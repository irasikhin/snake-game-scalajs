package ru.ir.snake

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.ext._
import org.scalajs.dom.raw.HTMLCanvasElement
import ru.ir.snake.SnakeGame._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}

class Game(node: Element, canvas: HTMLCanvasElement) {
  implicit val context = canvas.getContext("2d").cast[CanvasRenderingContext2D]
  val GAME_OVER = "GAME OVER"

  var grid = new Grid(canvas.width, canvas.height)
  var bounds = new Walls(grid)
  var food = new Food(grid)
  var snake = new Snake(grid)
  var interval = -1

  val keyCodes = Map(37 -> LEFT, 38 -> UP, 39 -> RIGHT, 40 -> DOWN)

  val keysHandler: js.Function1[dom.KeyboardEvent, Any] = (e: dom.KeyboardEvent) => {
    e.preventDefault()
    keyCodes.get(e.keyCode).foreach(direction => {
      snake.setDirection(direction)
      dom.clearInterval(interval)
      step()
      interval = dom.setInterval(() => {
        step()
      }, 100)
      false
    })
  }

  start()

  def start(): Unit = {
    interval = dom.setInterval(() => {
      step()
    }, 100)

    g.addEventListener("keyup", keysHandler, false)
  }

  def step(): Unit = {
    snake.step()
    food.step()
    bounds.step()

    if (snake.crashed) {
      end()
    }
  }

  def end() = {
    dom.clearInterval(interval)
    g.removeEventListener("keyup", keysHandler, false)
    context.font = "40px Arial"
    context.textAlign = "center"
    context.fillStyle = "black"
    context.fillText(GAME_OVER, canvas.width / 2, canvas.height / 2)
  }

  node.appendChild(canvas)
}
