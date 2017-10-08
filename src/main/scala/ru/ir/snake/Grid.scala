package ru.ir.snake

import org.scalajs.dom._
import ru.ir.snake.SnakeGame._

case class Cell(x: Int, y: Int)

class Grid(private val blockSize: Int, private val width: Int, private val height: Int)(implicit context: CanvasRenderingContext2D) {
  type Color = String

  val hRowsSize: Int = (width - (width % blockSize)) / blockSize
  val vRowsSize: Int = (height - (height % blockSize)) / blockSize

  private val colors: scala.collection.mutable.Map[Cell, Color] = scala.collection.mutable.Map[Cell, Color]()
  for (i <- 0 until hRowsSize; j <- 0 until vRowsSize) {
    setColor(Cell(i, j), CLEAN)
  }

  def getColor(cell: Cell): Color = colors.getOrElse(cell, CLEAN)

  def setColor(cell: Cell, color: Color): Unit = {
    context.fillStyle = color
    context.fillRect(cell.x * blockSize, cell.y * blockSize, blockSize, blockSize)
    colors.put(cell, color)
  }

  def clearColor(cell: Cell): Unit = {
    context.fillStyle = CLEAN
    context.fillRect(cell.x * blockSize, cell.y * blockSize, blockSize, blockSize)
    colors -= cell
  }

  def getCenterCell: Cell = Cell(hRowsSize / 2, vRowsSize / 2)

  def getFreeCells: Seq[Cell] = for {
    i <- 0 until vRowsSize
    j <- 0 until hRowsSize
    cell = Cell(j, i)
    if colors.getOrElse(cell, CLEAN) == CLEAN
  } yield cell

}
