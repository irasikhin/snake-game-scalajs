package ru.ir

import ru.ir.SnakeGame._

class Food(grid: Grid) {
  var current: Cell = null
  val random = scala.util.Random

  private def generate(): Cell = {
    val cells = grid.freeCells()
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