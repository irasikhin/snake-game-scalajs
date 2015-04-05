package ru.ir

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.ext._
import org.scalajs.dom.raw.{Event, HTMLCanvasElement}
import org.scalajs.jquery._
import ru.ir.SnakeGame._

import scala.scalajs.js
import scalatags.JsDom.all._
import scala.scalajs.js.Dynamic.{global => g}

class GameContext {
  var interval = 500
  var points = 0
}

class Game(node: Element, canvas: HTMLCanvasElement) {
  implicit val context = canvas.getContext("2d").cast[CanvasRenderingContext2D]

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
    jQuery(resultBlock).removeClass("hide")
  }

  def restart() = {
    jQuery(resultBlock).addClass("hide")
    grid = new Grid(canvas.width, canvas.height)
    bounds = new Walls(grid)
    food = new Food(grid)
    snake = new Snake(grid)
    start()
  }

  val restartButton = button("Restart", `class` := "btn btn-default").render
  restartButton.onclick = (e: Event) => {
    e.preventDefault()
    restart()
  }

  val resultBlock = div(`class` := "hide panel panel-primary results",
    div(`class` := "panel-heading", "Results"),
    div(`class` := "panel-body",
      div("Panel content", `class` := "row"),
      div(restartButton, `class` := "row")
    )
  ).render

  val battleFieldBlock = div(`class` := "battle-field",
    width := 500,
    height := 500,
    canvas, resultBlock)

  node.appendChild(battleFieldBlock.render)
}