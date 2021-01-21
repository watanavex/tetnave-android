package tech.watanave.tetnave.game

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class Game {

    private val size = Size(width = 10, height = 20)
    private val game = GameService(size, BlockSpecification())
    private val ticker = GameTicker()
    val gameState = game.fieldFlow.combine(game.blockFlow) { field, block ->
        val state: Array<Array<Cell>> = Array(size.height) { y ->
            Array(size.width) { x ->
                field.cells[y][x] as Cell
            }
        }
        block?.positions?.forEach { position ->
            state[position.y][position.x] = Cell.Moving
        }
        return@combine state
    }

    suspend fun start() {
        game.addNewBlock()

        ticker.tickFlow.collect {
            if (game.blockFlow.value == null) {
                game.addNewBlock()
                delay(200)
                return@collect
            }

            try {
                game.moveBlock(0, 1)
            } catch (e: Exception) {
            }
            game.fixedBlockIfNeed()
            delay(100)
            game.flushLinesIfNeed()
            delay(100)
        }
    }

    fun left() {
        try {
            game.moveBlock(-1, 0)
        } catch (e: Throwable) {

        }
    }

    fun right() {
        try {
            game.moveBlock(1, 0)
        } catch (e: Throwable) {

        }
    }

    fun down() {
        try {
            game.moveBlock(0, 1)
        } catch (e: Throwable) {

        }
    }

    fun rotate() {
        try {
            game.rotateBlock()
        } catch (e: Throwable) {

        }
    }
}

class GameTicker {

    val tickFlow: Flow<Unit> = flow {
        while (true) {
            delay(1_000L)
            Log.w("@@@", "GameTicker Tick")
            emit(Unit)
        }
    }

}
