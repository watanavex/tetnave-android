package tech.watanave.tetnave.game

import java.lang.Exception
import kotlin.math.absoluteValue

class Field (size: Size, private val blockSpecification: BlockSpecification) {

    var block: Block? = null; private set
    private val cells: Cells = Cells(size)

    fun addNewBlock(mapPattern: MapPattern) {
        val x = cells.size.width / 2
        val y = mapPattern.map { it.second }.minOrNull()!!.absoluteValue
        val newBlock = Block(Position(x, y), Block.Rotate.Top, mapPattern)

        if (!blockSpecification.isSatisfied(newBlock, cells)) {
            throw Exception()
        }

        this.block = newBlock
    }

    fun moveBlock(x: Int, y: Int) {
        if (y < 0) {
            throw Exception()
        }
        val block = this.block ?: throw Exception()
        val newBlock = block.move(x, y)

        if (!blockSpecification.isSatisfied(newBlock, cells)) {
            throw Exception()
        }

        this.block = newBlock
    }

    fun rotateBlock(rotate: Block.Rotate) {
        val block = this.block ?: throw Exception()
        val rotatedBlock = block.rotate(rotate)

        val newBlock = blockSpecification.adjust(rotatedBlock, cells) ?: throw Exception()

        if (!blockSpecification.isSatisfied(newBlock, cells)) {
            throw Exception()
        }

        this.block = newBlock
    }

    fun fixedBlock() {
        val block = this.block ?: throw Exception()
        val newBlock = block.move(0, 1)

        if (blockSpecification.isSatisfied(newBlock, cells)) {
            return
        }

        block.positions.forEach { position ->
            cells[position] = Cell.Fixed
        }
    }

    fun flushLines() : Int {
        var lineCount = 0
        (0 until cells.size.height).reversed().forEach { y ->
            if (!cells.array[y].all { it == Cell.Fixed }) {
                return@forEach
            }

            lineCount += 1
            cells.shiftDown(y)
        }
        return lineCount
    }
}

class BlockSpecification {

    fun isSatisfied(block: Block, cells: Cells) : Boolean {
        block.positions.forEach { position ->
            if (isSatisfied(position, cells)) {
                return false
            }
        }

        return true
    }

    private fun isSatisfied(position: Position, cells: Cells) : Boolean {
        if (position.x < 0 || position.y < 0 || position.x >= cells.size.width || position.y >= cells.size.height) {
            return false
        }
        if (cells[position] != Cell.None) {
            return false
        }
        return true
    }

    fun adjust(block: Block, cells: Cells) : Block? {
        var offsetX = 0
        var offsetY = 0

        block.positions
                .filter { !isSatisfied(it, cells) }
                .forEach { position ->
                    offsetX += position.x - block.origin.x
                    offsetY += position.y - block.origin.y
        }
        val newBlock = block.move(-offsetX, -offsetY)

        if (!isSatisfied(newBlock, cells)) {
            return null
        }

        return newBlock
    }
}
