package ru.ir.snake

import SnakeGame._

class Food(grid: Grid) {
  var current: Cell = null
  val random = scala.util.Random

  private def generate(): Cell = {
    val cells = grid.getFreeCells
    val cell = cells(random.nextInt(cells.size))
    grid.put(cell, FOOD)
    cell
  }

  def step() = {
    if (current == null || grid.get(current) != FOOD) {
      current = generate()
    }
  }
}
