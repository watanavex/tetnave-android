package tech.watanave.tetnave.game

import androidx.compose.material.rememberBottomDrawerState
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import kotlin.math.absoluteValue

interface GameSpecification {
    fun isSatisfied(block: Block, field: Field) : Boolean
    fun isSatisfied(position: Position, field: Field) : Boolean
}

interface GameRepository {

    val blockFlow: SharedFlow<Block?>
    val fieldFlow: SharedFlow<Field>

    suspend fun getBlock() : Block?
    suspend fun getField() : Field?

    suspend fun updateBlock(block: Block?)
    suspend fun updateField(field: Field)

}

class GameUseCaseImpl @Inject constructor(private val size: Size,
                                          private val repository: GameRepository,
                                          private val specification: GameSpecification) : GameUseCase {

    override suspend fun initializeField() {
        repository.updateField(Field(size))
    }

    override suspend fun addNewBlock() {
        val mapPattern = listOf(Block.Pattern1, Block.Pattern2, Block.Pattern3, Block.Pattern4).random()
        val field = repository.getField() ?: throw IllegalStateException()
        val x = field.size.width / 2 - 1
        val y = mapPattern.map { it.second }.minOrNull()!!.absoluteValue
        val newBlock = Block(Position(x, y), Block.Rotate.Top, mapPattern)

        if (!specification.isSatisfied(newBlock, field)) {
            throw Exception()
        }

        repository.updateBlock(newBlock)
    }

    override suspend fun moveBlock(x: Int, y: Int) {
        if (y < 0) {
            throw Exception()
        }

        val block = repository.getBlock() ?: throw Exception()
        val field = repository.getField() ?: throw IllegalStateException()
        val newBlock = block.move(x, y)

        if (!specification.isSatisfied(newBlock, field)) {
            throw Exception()
        }

        repository.updateBlock(newBlock)
    }

    override suspend fun rotateBlock() {
        val block = repository.getBlock() ?: throw Exception()
        val field = repository.getField() ?: throw IllegalStateException()

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

        repository.updateBlock(newBlock)
    }

    override suspend fun fixedBlockIfNeed() {
        val block = repository.getBlock() ?: throw Exception()
        val newField = repository.getField()?.copy() ?: throw IllegalStateException()

        val newBlock = block.move(0, 1)

        if (specification.isSatisfied(newBlock, newField)) {
            return
        }

        block.positions.forEach { position ->
            newField[position] = Cell.Fixed
        }

        repository.updateBlock(block)
        repository.updateField(newField)
    }

    override suspend fun flushLinesIfNeed() {
        val newField = repository.getField()?.copy() ?: throw IllegalStateException()

        (0 until newField.size.height).reversed().forEach { y ->
            if (!newField.cells[y].all { it == Cell.Fixed }) {
                return@forEach
            }
            newField.shiftDown(y)
        }

        repository.updateField(newField)
    }
}
