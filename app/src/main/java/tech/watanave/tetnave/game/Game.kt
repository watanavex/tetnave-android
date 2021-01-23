package tech.watanave.tetnave.game

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface GameService {

    val blockFlow: MutableStateFlow<Block?>
    val fieldFlow: MutableStateFlow<Field>

    fun addNewBlock()
    fun moveBlock(x: Int, y: Int)
    fun rotateBlock()
    fun fixedBlockIfNeed()
    fun flushLinesIfNeed()

}

interface GameTicker {

    val tickFlow: Flow<Unit>

}

interface Game {
    val gameField: Flow<Array<Array<Cell>>>
    suspend fun start()
    fun left()
    fun right()
    fun down()
    fun rotate()
}

class GameImpl @Inject constructor(private val gameService: GameService,
                                   private val ticker: GameTicker) : Game {



    override val gameField = gameService.fieldFlow.combine(gameService.blockFlow) { field, block ->
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
        gameService.addNewBlock()

        ticker.tickFlow.collect {
            if (gameService.blockFlow.value == null) {
                gameService.addNewBlock()
                delay(200)
                return@collect
            }

            try {
                gameService.moveBlock(0, 1)
            } catch (e: Exception) {
            }
            gameService.fixedBlockIfNeed()
            delay(100)
            gameService.flushLinesIfNeed()
            delay(100)
        }
    }

    override fun left() {
        try {
            gameService.moveBlock(-1, 0)
        } catch (e: Throwable) {

        }
    }

    override fun right() {
        try {
            gameService.moveBlock(1, 0)
        } catch (e: Throwable) {

        }
    }

    override fun down() {
        try {
            gameService.moveBlock(0, 1)
        } catch (e: Throwable) {

        }
    }

    override fun rotate() {
        try {
            gameService.rotateBlock()
        } catch (e: Throwable) {

        }
    }
}
