package tech.watanave.tetnave.game

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface GameUseCase {

    suspend fun initializeField()
    suspend fun addNewBlock()
    suspend fun moveBlock(x: Int, y: Int)
    suspend fun rotateBlock()
    suspend fun fixedBlockIfNeed()
    suspend fun flushLinesIfNeed()

}

interface GameTicker {

    val tickFlow: Flow<Unit>

}

interface GameService {
    val gameField: Flow<Array<Array<Cell>>>
    suspend fun start()
    suspend fun left()
    suspend fun right()
    suspend fun down()
    suspend fun rotate()
}

class GameServiceImpl @Inject constructor(private val useCase: GameUseCase,
                                          private val repository: GameRepository,
                                          private val ticker: GameTicker) : GameService {



    override val gameField = repository.fieldFlow.combine(repository.blockFlow) { field, block ->
        val state: Array<Array<Cell>> = Array(field.size.height) { y ->
            Array(field.size.width) { x ->
                field.cells[y][x] as Cell
            }
        }
        block?.positions?.forEach { position ->
            state[position.y][position.x] = Cell.Moving
        }
        return@combine state
    }

    override suspend fun start() {
        useCase.initializeField()
        useCase.addNewBlock()

        ticker.tickFlow.collect {
            if (repository.getBlock() == null) {
                useCase.addNewBlock()
                delay(200)
                return@collect
            }

            try {
                useCase.moveBlock(0, 1)
            } catch (e: Exception) {
            }
            useCase.fixedBlockIfNeed()
            delay(100)
            useCase.flushLinesIfNeed()
            delay(100)
        }
    }

    override suspend fun left() {
        try {
            useCase.moveBlock(-1, 0)
        } catch (e: Throwable) {

        }
    }

    override suspend fun right() {
        try {
            useCase.moveBlock(1, 0)
        } catch (e: Throwable) {

        }
    }

    override suspend fun down() {
        try {
            useCase.moveBlock(0, 1)
        } catch (e: Throwable) {

        }
    }

    override suspend fun rotate() {
        try {
            useCase.rotateBlock()
        } catch (e: Throwable) {

        }
    }
}
