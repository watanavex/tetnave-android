package tech.watanave.tetnave.game

enum class Cell {
    Fixed, None
}

class Cells private constructor(val array: Array<Array<Cell>>, val size: Size) {

    constructor(size: Size): this(Array(size.height) {
        Array(size.width) { Cell.None }
    }, size)

    operator fun get(position: Position) : Cell {
        return array[position.y][position.x]
    }

    operator fun set(position: Position, cell: Cell) {
        array[position.y][position.x] = cell
    }

    fun shiftDown(lineIndex: Int) {
        (0 until lineIndex).reversed().forEach { y ->
            array[y + 1] = array[y]
        }
    }
}
