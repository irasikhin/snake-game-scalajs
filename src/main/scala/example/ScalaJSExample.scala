package example

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.ext._
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}

object ScalaJSExample extends js.JSApp {

  val BLOCK_EDGE_SIZE = 10
  val UP = 0
  val DOWN = 1
  val LEFT = 2
  val RIGHT = 3

  def main(): Unit = {
    val canvas = dom.document.createElement("canvas").cast[HTMLCanvasElement]
    val context = canvas.getContext("2d").cast[CanvasRenderingContext2D]
    canvas.width = (0.3 * g.window.innerWidth.cast[Int]).toInt
    canvas.height = (0.25 * g.window.innerHeight.cast[Int]).toInt
    canvas.width = canvas.width - (canvas.width % 10)
    canvas.height = canvas.width - (canvas.height % 10)
    dom.document.getElementById("playground").appendChild(canvas)
    val vertical = canvas.width / 10
    val horizontal = canvas.width / 10
    val grid = new Grid(context, canvas.width, canvas.height)
    val snake = Snake(grid)

    g.addEventListener("keyup", (e: dom.KeyboardEvent) => {
      e.preventDefault()
      e.keyCode match {
        //left
        case 37 => snake.setDirection(LEFT)
        // up
        case 38 => snake.setDirection(UP)
        // right
        case 39 => snake.setDirection(RIGHT)
        // down
        case 40 => snake.setDirection(DOWN)
        case _ =>
      }
      snake.move()
      false
    }, false)


    dom.setInterval(() => {
      snake.move()
    }, 500)
  }

  case class Snake(grid: Grid) {
    val baseSize = 3
    private val center = grid.center()
    var snake: Array[Cell] = Array(center, Cell(center.x, center.y - 1), Cell(center.x, center.y - 2))
    var direction: Int = 0

      draw()

    def draw(): Unit = {
      snake.foreach(grid.fill)
    }

    def hide(): Unit = {
      snake.foreach(grid.unfill)
    }

    def setDirection(direction: Int): Unit = {
      this.direction = direction
    }

    def move() = {
      hide()
      println("snake: " + snake.toList)
      snake = snake.tail :+ (this.direction match {
        case UP => Cell(snake.last.x, snake.last.y - 1)
        case DOWN => Cell(snake.last.x, snake.last.y + 1)
        case LEFT => Cell(snake.last.x - 1, snake.last.y)
        case RIGHT => Cell(snake.last.x + 1, snake.last.y)
      })
      println(snake.toList)
      draw()
    }
  }

  case class Block(context: CanvasRenderingContext2D, x: Int, y: Int, size: Int) {
    val BLACK = "#000000"
    val WHITE = "#FFFFFF"

    def border(): Block = {
      context.lineWidth = 2
      context.strokeStyle = BLACK
      context.rect(x, y, size, size)
      this
    }

    def fill(): Block = {
      context.fillStyle = BLACK
      context.fillRect(x, y, size, size)
      this
    }

    def unfill(): Block = {
      context.fillStyle = WHITE
      context.fillRect(x, y, size, size)
      this
    }
  }

  case class Cell(x: Int, y: Int)

  class Grid(context: CanvasRenderingContext2D, width: Int, height: Int) {
    val blockSize = 10
    private val horizontal = (width - (width % blockSize)) / blockSize
    private val vertical = (height - (height % blockSize)) / blockSize
    private val blocks: Seq[Seq[Block]] = for (i <- 0 until vertical) yield for (j <- 0 until horizontal)
    yield Block(context, j * blockSize, i * blockSize, blockSize).border()

    for {
      i <- 0 until blocks.size
      j <- 0 until blocks(i).size
    } {
      if (i == 0 || i == blocks.size - 1 || j == 0 || j == blocks(i).size - 1) {
        blocks(i)(j).fill()
      }
    }

    def fill(cell: Cell) = {
      blocks(cell.y)(cell.x).fill()
    }

    def unfill(cell: Cell) = {
      blocks(cell.y)(cell.x).unfill()
    }

    def center(): Cell = {
      val i: Int = blocks.size / 2
      val j: Int = blocks(i).size / 2
      Cell(j, i)
    }

    def cell(x: Int, y: Int): Block = blocks(y)(x)

  }

}
