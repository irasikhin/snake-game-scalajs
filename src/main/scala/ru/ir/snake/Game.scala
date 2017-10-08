package ru.ir.snake

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.ext._
import org.scalajs.dom.raw.HTMLCanvasElement
import ru.ir.snake.SnakeGame._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}

class Game(private val blockSize: Int, private val node: Element, private val canvas: HTMLCanvasElement) extends UiStep {
  private implicit val context: CanvasRenderingContext2D = canvas.getContext("2d").cast[CanvasRenderingContext2D]
  private val GAME_OVER = "GAME OVER"
  private val KEY_CODES = Map(37 -> LEFT, 38 -> UP, 39 -> RIGHT, 40 -> DOWN)

  private val grid = new Grid(blockSize, canvas.width, canvas.height)
  private val walls = new Walls(grid)
  private val food = new Food(grid)
  private val snake = new Snake(grid)
  private var interval = -1

  private val keysHandler: js.Function1[dom.KeyboardEvent, Any] = (e: dom.KeyboardEvent) => {
    e.preventDefault()
    KEY_CODES.get(e.keyCode).foreach(direction => {
      snake.setDirection(direction)
      dom.clearInterval(interval)
      doStep()
      interval = dom.setInterval(() => {
        doStep()
      }, 100)
      false
    })
  }

  start()

  def start(): Unit = {
    interval = dom.setInterval(() => {
      doStep()
    }, 100)

    g.addEventListener("keyup", keysHandler, false)
  }

  override def doStep(): Unit = {
    snake.doStep()
    food.doStep()
    walls.doStep()

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
