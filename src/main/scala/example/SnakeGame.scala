package example

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.ext._
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}

object SnakeGame extends js.JSApp {

  val BLOCK_EDGE_SIZE = 15
  val UP = 0
  val DOWN = 1
  val LEFT = 2
  val RIGHT = 3

  val CLEAN = "#FFFFFF"
  val FOOD = "#67EBAD"
  val BOUND = "#000000"
  val SNAKE = "#1D5FDB"
  val SNAKE_HEAD = "#0B157D"

  trait Observable {
    def on(event: String)

    def notify(event: String)
  }

  def main(): Unit = {
    val canvas = dom.document.createElement("canvas").cast[HTMLCanvasElement]
    canvas.width = (0.3 * g.window.innerWidth.cast[Int]).toInt
    canvas.height = (0.25 * g.window.innerHeight.cast[Int]).toInt
    canvas.width = canvas.width - (canvas.width % BLOCK_EDGE_SIZE)
    canvas.height = canvas.width - (canvas.height % BLOCK_EDGE_SIZE)
    dom.document.getElementById("playground").appendChild(canvas)

    val game = new Game(canvas)
  }

  class GameContext {
    var interval = 500
    var points = 0
  }

  class Game(canvas: HTMLCanvasElement) {
    implicit val context = canvas.getContext("2d").cast[CanvasRenderingContext2D]

    val grid = new Grid(canvas.width, canvas.height)
    val bounds = new Bounds(grid)
    val food = new Food(grid)
    val snake = new Snake(grid)

    val keyCodes = Map(37 -> LEFT, 38 -> UP, 39 -> RIGHT, 40 -> DOWN)

    start()

    def start(): Unit = {
      var interval = dom.setInterval(() => {
        step()
      }, 100)

      g.addEventListener("keyup", (e: dom.KeyboardEvent) => {
        e.preventDefault()
        keyCodes.get(e.keyCode).foreach(direction => {
          snake.setDirection(direction)
          dom.clearInterval(interval)
          step()
          interval = dom.setInterval(() => {
            step()
          }, 100)
        })
        false
      }, false)
    }

    def step() = {
      snake.step()
      food.step()
      bounds.step()
    }

    def end() = {

    }
  }

  class Snake(grid: Grid) {
    val baseSize = 3
    private val center = grid.center()
    var snake: Array[Cell] = Array(center, Cell(center.x, center.y - 1), Cell(center.x, center.y - 2))
    var direction: Int = 0

    def draw(): Unit = {
      grid.put(snake.last, SNAKE_HEAD)
      snake.init.foreach(cell => grid.put(cell, SNAKE))
    }

    def hide(): Unit = {
      snake.foreach(grid.clear)
    }

    def setDirection(direction: Int): Unit = {
      if (!(this.direction == RIGHT && direction == LEFT ||
        this.direction == LEFT && direction == RIGHT ||
        this.direction == UP && direction == DOWN) && !(this.direction == DOWN && direction == UP)) {
        this.direction = direction
      }
    }

    def step(): Unit = {
      hide()
      val cell = this.direction match {
        case UP => Cell(snake.last.x, snake.last.y - 1)
        case DOWN => Cell(snake.last.x, snake.last.y + 1)
        case LEFT => Cell(snake.last.x - 1, snake.last.y)
        case RIGHT => Cell(snake.last.x + 1, snake.last.y)
      }
      grid.get(cell) match {
        case FOOD => snake = snake.:+(cell)
        case CLEAN => snake = snake.tail.:+(cell)
        case BOUND => // game over
        case SNAKE => // game over
      }
      draw()
    }
  }

  class Food(grid: Grid) {
    var current: Cell = null
    val random = scala.util.Random

    def create(): Cell = {
      val cells = grid.freeCells()
      val cell = cells(random.nextInt(cells.size))
      grid.put(cell, FOOD)
      cell
    }

    def step() = {
      if (current == null || grid.get(current) != FOOD) {
        current = create()
      }
    }
  }

  class Bounds(grid: Grid) {
    val bounds = for {
      i <- 0 until grid.vertical
      j <- 0 until grid.horizontal
      if i == 0 || i == grid.vertical - 1 || j == 0 || j == grid.horizontal - 1
    } yield Cell(j, i)

    bounds.foreach(grid.put(_, BOUND))

    def step() = {
      //      bounds.foreach(cell => {
      //        if (grid.get(cell) != BOUND) {
      //          throw new IllegalStateException()
      //        }
      //      })
    }
  }

  case class Cell(x: Int, y: Int)

  class Grid(width: Int, height: Int)(implicit context: CanvasRenderingContext2D) {
    type Color = String

    val blockSize = 15
    val horizontal = (width - (width % blockSize)) / blockSize
    val vertical = (height - (height % blockSize)) / blockSize
    private val bucket: scala.collection.mutable.Map[Cell, Color] = scala.collection.mutable.Map[Cell, Color]()

    def get(cell: Cell): Color = bucket.getOrElse(cell, CLEAN)

    def put(cell: Cell, color: Color): Unit = {
      context.fillStyle = color
      context.fillRect(cell.x * blockSize, cell.y * blockSize, blockSize, blockSize)
      bucket.put(cell, color)
    }

    def clear(cell: Cell) = {
      context.fillStyle = CLEAN
      context.fillRect(cell.x * blockSize, cell.y * blockSize, blockSize, blockSize)
      bucket -= cell
    }

    def center(): Cell = {
      val i: Int = vertical / 2
      val j: Int = horizontal / 2
      Cell(j, i)
    }

    def freeCells(): Seq[Cell] = for {
      i <- 0 until vertical
      j <- 0 until horizontal
      cell = Cell(j, i)
      if !bucket.contains(cell)
    } yield cell

  }

}
