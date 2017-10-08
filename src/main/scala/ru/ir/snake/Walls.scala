package ru.ir.snake

import ru.ir.snake.SnakeGame._

class Walls(private val grid: Grid) extends UiStep {
  private val walls = for {
    i <- 0 until grid.vRowsSize
    j <- 0 until grid.hRowsSize
    if i == 0 || i == grid.vRowsSize - 1 || j == 0 || j == grid.hRowsSize - 1
  } yield Cell(j, i)

  walls.foreach(grid.setColor(_, WALL))

  override def doStep(): Unit = {
  }
}
