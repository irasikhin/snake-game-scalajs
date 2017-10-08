package ru.ir.snake

import ru.ir.snake.SnakeGame._

class Food(private val grid: Grid) extends UiStep {
  private val random = scala.util.Random
  private var current: Option[Cell] = None

  def doStep(): Unit = {
    current = current match {
      case Some(c) => if (grid.getColor(c) != FOOD) {
        generateNext()
      } else {
        current
      }
      case None => generateNext()
    }
  }

  private def generateNext(): Option[Cell] = Option(grid.getFreeCells).filter(_.nonEmpty).map(cells => {
    val cell = cells(random.nextInt(cells.size))
    grid.setColor(cell, FOOD)
    cell
  })
}
