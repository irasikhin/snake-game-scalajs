package ru.ir

import ru.ir.SnakeGame._

class Snake(grid: Grid) {
  val baseSize = 3
  private val center = grid.center()
  var snake: Array[Cell] = Array(center, Cell(center.x, center.y - 1), Cell(center.x, center.y - 2))
  var direction: Int = 0
  var crashed = false

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
      case WALL => crashed = true // game over
      case SNAKE => crashed = true // game over
    }
    draw()
  }
}