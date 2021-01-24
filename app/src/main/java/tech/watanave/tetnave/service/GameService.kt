package tech.watanave.tetnave.service

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import tech.watanave.tetnave.domain.value.Cell
import tech.watanave.tetnave.domain.GameRepository
import javax.inject.Inject

interface GameUseCase {

    fun initializeField()
    fun addNewBlock()
    fun moveBlock(x: Int, y: Int)
    fun rotateBlock()
    fun fixedBlockIfNeed()
    fun flushLinesIfNeed()

}

interface GameTicker {

    val tickFlow: Flow<Unit>

}

interface GameService {
    val gameField: Flow<Array<Array<Cell>>>
    suspend fun start()
    fun left()
    fun right()
    fun down()
    fun rotate()
}

class GameServiceImpl @Inject constructor(private val useCase: GameUseCase,
                                          private val repository: GameRepository,
                                          private val ticker: GameTicker) : GameService {



    override val gameField = repository.fieldFlow.filterNotNull().combine(repository.blockFlow) { field, block ->
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
                Log.w("@@@", "addNewBlock")
                delay(200)
                return@collect
            }

            try {
                useCase.moveBlock(0, 1)
                Log.w("@@@", "moveBlock")
            } catch (e: Exception) {
            }
            useCase.fixedBlockIfNeed()
            delay(100)
            useCase.flushLinesIfNeed()
            delay(100)
        }
    }

    override fun left() {
        try {
            useCase.moveBlock(-1, 0)
        } catch (e: Throwable) {

        }
    }

    override fun right() {
        try {
            useCase.moveBlock(1, 0)
        } catch (e: Throwable) {

        }
    }

    override fun down() {
        try {
            useCase.moveBlock(0, 1)
        } catch (e: Throwable) {

        }
    }

    override fun rotate() {
        try {
            useCase.rotateBlock()
        } catch (e: Throwable) {

        }
    }
}
