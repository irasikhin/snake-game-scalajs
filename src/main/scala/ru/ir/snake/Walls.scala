package ru.ir.snake

import SnakeGame._

class Walls(grid: Grid) {
  val walls = for {
    i <- 0 until grid.vertical
    j <- 0 until grid.horizontal
    if i == 0 || i == grid.vertical - 1 || j == 0 || j == grid.horizontal - 1
  } yield Cell(j, i)

  walls.foreach(grid.put(_, WALL))

  def step() = {
    //      bounds.foreach(cell => {
    //        if (grid.get(cell) != BOUND) {
    //          throw new IllegalStateException()
    //        }
    //      })
  }
}
