package ru.ir.snake

import ru.ir.snake.SnakeGame._

class Snake(private val grid: Grid) extends UiStep {
  var crashed = false
  private val center = grid.getCenterCell
  private var snake: Array[Cell] = Array(center, Cell(center.x, center.y - 1), Cell(center.x, center.y - 2))
  private var direction: Int = 0

  def setDirection(direction: Int): Unit = {
    if (!(this.direction == RIGHT && direction == LEFT || this.direction == LEFT && direction == RIGHT ||
      this.direction == UP && direction == DOWN) && !(this.direction == DOWN && direction == UP)) {
      this.direction = direction
    }
  }

  override def doStep(): Unit = {
    val oldSnake = snake
    val last = oldSnake.last
    val cell = this.direction match {
      case UP => Cell(last.x, last.y - 1)
      case DOWN => Cell(last.x, last.y + 1)
      case LEFT => Cell(last.x - 1, last.y)
      case RIGHT => Cell(last.x + 1, last.y)
    }
    grid.getColor(cell) match {
      case FOOD => snake = snake.:+(cell)
      case CLEAN => snake = snake.tail.:+(cell)
      case WALL => crashed = true // game over
      case SNAKE => crashed = true // game over
    }
    hide(oldSnake)
    draw(snake)
  }

  private def draw(snake: Array[Cell]): Unit = {
    grid.setColor(snake.last, SNAKE_HEAD)
    snake.init.foreach(cell => grid.setColor(cell, SNAKE))
  }

  private def hide(snake: Array[Cell]): Unit = {
    snake.foreach(grid.clearColor)
  }
}
