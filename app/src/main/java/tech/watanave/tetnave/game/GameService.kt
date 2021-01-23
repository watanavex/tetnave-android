package tech.watanave.tetnave.game

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.absoluteValue

interface GameSpecification {
    fun isSatisfied(block: Block, field: Field) : Boolean
    fun isSatisfied(position: Position, field: Field) : Boolean
}

class GameServiceImpl @Inject constructor(size: Size, private val specification: GameSpecification) : GameService {

    override val blockFlow = MutableStateFlow<Block?>(null)
    override val fieldFlow = MutableStateFlow(Field(size))

    override fun addNewBlock() {
        val mapPattern = listOf(Block.Pattern1, Block.Pattern2, Block.Pattern3, Block.Pattern4).random()
        val field = fieldFlow.value
        val x = field.size.width / 2 - 1
        val y = mapPattern.map { it.second }.minOrNull()!!.absoluteValue
        val newBlock = Block(Position(x, y), Block.Rotate.Top, mapPattern)

        if (!specification.isSatisfied(newBlock, field)) {
            throw Exception()
        }

        blockFlow.value = newBlock
    }

    override fun moveBlock(x: Int, y: Int) {
        if (y < 0) {
            throw Exception()
        }

        val block = this.blockFlow.value ?: throw Exception()
        val field = fieldFlow.value
        val newBlock = block.move(x, y)

        if (!specification.isSatisfied(newBlock, field)) {
            throw Exception()
        }

        blockFlow.value = newBlock
    }

    override fun rotateBlock() {
        val block = this.blockFlow.value ?: throw Exception()
        val field = fieldFlow.value

        var ordinal = block.rotate.ordinal + 1
        if (Block.Rotate.values().size <= ordinal) {
            ordinal = 0
        }
        val rotate = Block.Rotate.values()[ordinal]
        val rotatedBlock = block.rotate(rotate)

        var offsetX = 0
        var offsetY = 0
        rotatedBlock.positions.filter { !specification.isSatisfied(it, field) }
                .forEach { position ->
                    offsetX = position.x - block.origin.x
                    offsetY = position.y - block.origin.y
                }

        val newBlock = rotatedBlock.move(-offsetX, -offsetY)

        if (!specification.isSatisfied(newBlock, field)) {
            throw Exception()
        }

        blockFlow.value = newBlock
    }

    override fun fixedBlockIfNeed() {
        val block = this.blockFlow.value ?: throw Exception()
        val field = fieldFlow.value.copy()

        val newBlock = block.move(0, 1)

        if (specification.isSatisfied(newBlock, field)) {
            return
        }

        block.positions.forEach { position ->
            field[position] = Cell.Fixed
        }

        blockFlow.value = null
        fieldFlow.value = field
    }

    override fun flushLinesIfNeed() {
        val field = fieldFlow.value

        (0 until field.size.height).reversed().forEach { y ->
            if (!field.cells[y].all { it == Cell.Fixed }) {
                return@forEach
            }
            field.shiftDown(y)
        }
    }
}
