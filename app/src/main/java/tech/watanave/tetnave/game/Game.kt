package tech.watanave.tetnave.game

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


typealias GameCells = MutableList<MutableList<Game.Cell>>
class Game {

    private val size = Size(width = 10, height = 20)
    private val ticker = GameTicker()
    private val _cells = MutableStateFlow(
            Array(size.height) {
                Array(size.width) { Cell.None }.toMutableList()
            }.toMutableList()
    )
    val cell: StateFlow<GameCells> = _cells
    private val _counter = MutableStateFlow(0)
    val counter = _counter
    private val _list = MutableStateFlow(listOf(0, 0, 0))
    val list = _list
    private val _unit = MutableStateFlow(Unit)
    val unit = _unit

    enum class Cell {
        Fixed, Moving, None
    }

    suspend fun start() {
        ticker.tickFlow.collect {
            val cells = _cells.value

            loop@ for (y in 0 until cells.size) {
                for (x in 0 until cells[y].size) {
                    if (cells[y][x] == Cell.None) {
                        cells[y][x] = Cell.Fixed
                        break@loop
                    }
                }
            }
//            _counter.value = cells.flatten()
//                .count { it != Cell.Fixed }
//            _list.value = listOf(0, 0, 0)

            val empty: GameCells = mutableListOf()
            cells.forEach { empty.add(it) }

            val check1 = listOf(1) == listOf(1)
            val check2 = listOf(1, 2, 3) == listOf(1, 2, 4)
            val check3 = listOf(listOf(1, 2, 3)) == listOf(listOf(1, 2, 4))
            val check = empty == _cells.value
            val c2 = _cells.value

            _cells.value = empty

//            _unit.emit(Unit)
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