package tech.watanave.tetnave.game

import android.util.Log
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay


class Game(val size: Size = Size(width = 10, height = 20)) {

    enum class Cell {
        Fixed, Temporary, None
    }

    private val fixedCells = (0 until size.height).map {
        (0 until size.width).map {
            Cell.None
        }.toMutableList()
    }.toMutableList()

    var cells by mutableStateOf(
            (0 until size.height).map {
                (0 until size.width).map {
                    Cell.None
                }.toMutableList()
            }.toMutableList()
        )
        private set

    private var temporaryBlock: Block = Block(origin = Position(4, 1), rotate = Block.Rotate.Top, mapPattern = Block.Pattern3)

    suspend fun start() {
        merge(temporaryBlock)
        while (true) {
            Log.w("@@@", "Polling ...")
            delay(500)
            down()
            if (fallCheck(temporaryBlock)) {
                continue
            }

            fixed(temporaryBlock)
            if (flushLine()) {
                delay(500)
            }
            temporaryBlock = Block(origin = Position(4, 1), rotate = Block.Rotate.Top, mapPattern = listOf(Block.Pattern1, Block.Pattern2, Block.Pattern3).random())
            merge(temporaryBlock)
        }
    }

    fun left() {
        val newBlock = temporaryBlock.move(-1, 0)
        if (!check(newBlock)) {
            return
        }
        temporaryBlock = newBlock
        merge(temporaryBlock)
    }

    fun right() {
        val newBlock = temporaryBlock.move(1, 0)
        if (!check(newBlock)) {
            return
        }
        temporaryBlock = newBlock
        merge(temporaryBlock)
    }

    fun down() {
        val newBlock = temporaryBlock.move(0, 1)
        if (!check(newBlock)) {
            return
        }
        temporaryBlock = newBlock
        merge(temporaryBlock)
    }

    fun rotate() {
        val newBlock = temporaryBlock.rotate()
        if (!check(newBlock)) {
            return
        }
        temporaryBlock = newBlock
        merge(temporaryBlock)
    }

    fun merge(block: Block) : MutableList<MutableList<Cell>> {
        val newCells = mutableListOf<MutableList<Cell>>()
        fixedCells.forEach { row -> newCells.add(row.toMutableList()) }
        block.positions.forEach { position ->
            newCells[position.y][position.x] = Cell.Temporary
        }
        cells = newCells
        return newCells
    }

    fun check(block: Block) : Boolean {
        val positions = block.positions
        check(positions.isNotEmpty())

        if (positions.map(Position::x).minOrNull()!! < 0) {
            return false
        }
        if (block.positions.map(Position::x).maxOrNull()!! > size.width - 1) {
            return false
        }
        if (block.positions.map(Position::y).minOrNull()!! < 0) {
            return false
        }
        if (block.positions.map(Position::y).maxOrNull()!! > size.height - 1) {
            return false
        }

        return block.positions.firstOrNull { position ->
            fixedCells[position.y][position.x] != Cell.None
        } == null
    }

    fun fallCheck(block: Block) : Boolean {
        val positions = block.positions
        check(positions.isNotEmpty())

        positions.forEach { position ->
            if (position.y == size.height - 1) {
                return false
            }
            if (fixedCells[position.y + 1][position.x] == Cell.Fixed) {
                return false
            }
        }
        return true
    }

    fun flushLine() : Boolean {
        var didFlush = false
        (0 until size.height).reversed().forEach { y ->
            if (!fixedCells[y].all { it == Cell.Fixed }) {
                return@forEach
            }
            didFlush = true
            (0 until y).reversed().forEach { offsetY ->
                fixedCells[offsetY + 1] = fixedCells[offsetY]
            }
        }
        return didFlush
    }

    fun fixed(block: Block) {
        val positions = block.positions
        check(positions.isNotEmpty())

        positions.forEach { position ->
            fixedCells[position.y][position.x] = Cell.Fixed
        }
    }
}