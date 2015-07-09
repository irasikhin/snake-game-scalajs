package ru.ir

import org.scalajs.dom._
import ru.ir.SnakeGame._

case class Cell(x: Int, y: Int)

class Grid(width: Int, height: Int)(implicit context: CanvasRenderingContext2D) {
  type Color = String

  val blockSize = 15
  val horizontal = (width - (width % blockSize)) / blockSize
  val vertical = (height - (height % blockSize)) / blockSize
  private val bucket: scala.collection.mutable.Map[Cell, Color] = scala.collection.mutable.Map[Cell, Color]()

  refresh()

  def refresh(): Unit = {
    for (i <- 0 until horizontal; j <- 0 until vertical) {
      put(Cell(i, j), CLEAN)
    }
  }

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

  def getCenterCell: Cell = {
    val i: Int = vertical / 2
    val j: Int = horizontal / 2
    Cell(j, i)
  }

  def getFreeCells: Seq[Cell] = for {
    i <- 0 until vertical
    j <- 0 until horizontal
    cell = Cell(j, i)
    if !bucket.contains(cell)
  } yield cell

}
