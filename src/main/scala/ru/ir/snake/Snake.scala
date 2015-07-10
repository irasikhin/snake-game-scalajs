package ru.ir.snake

import SnakeGame._

class Snake(grid: Grid) {
  val baseSize = 3
  private val center = grid.getCenterCell
  var snake: Array[Cell] = Array(center, Cell(center.x, center.y - 1), Cell(center.x, center.y - 2))
  var direction: Int = 0
  var crashed = false

  private def draw(snake: Array[Cell]): Unit = {
    grid.put(snake.last, SNAKE_HEAD)
    snake.init.foreach(cell => grid.put(cell, SNAKE))
  }

  private def hide(snake: Array[Cell]): Unit = {
    snake.foreach(grid.clear)
  }

  def setDirection(direction: Int): Unit = {
    if (!(this.direction == RIGHT && direction == LEFT || this.direction == LEFT && direction == RIGHT ||
      this.direction == UP && direction == DOWN) && !(this.direction == DOWN && direction == UP)) {
      this.direction = direction
    }
  }

  def step(): Unit = {
    val oldSnake = snake
    val last = oldSnake.last
    val cell = this.direction match {
      case UP => Cell(last.x, last.y - 1)
      case DOWN => Cell(last.x, last.y + 1)
      case LEFT => Cell(last.x - 1, last.y)
      case RIGHT => Cell(last.x + 1, last.y)
    }
    grid.get(cell) match {
      case FOOD => snake = snake.:+(cell)
      case CLEAN => snake = snake.tail.:+(cell)
      case WALL => crashed = true // game over
      case SNAKE => crashed = true // game over
    }
    hide(oldSnake)
    draw(snake)
  }
}
