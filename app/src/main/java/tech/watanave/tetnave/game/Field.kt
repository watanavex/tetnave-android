package tech.watanave.tetnave.game

class Field private constructor(val cells: Array<Array<FieldCell>>, val size: Size) {

    constructor(size: Size): this(Array(size.height) {
        Array(size.width) { Cell.None }
    }, size)

    fun copy() : Field {
        val newArray = Array(size.height) { y ->
            Array(size.width) { x ->
                cells[y][x]
            }
        }
        return Field(newArray, size)
    }

    operator fun get(position: Position) : FieldCell {
        return cells[position.y][position.x]
    }

    operator fun set(position: Position, cell: FieldCell) {
        cells[position.y][position.x] = cell
    }

    override fun hashCode(): Int {
        return cells.hashCode() * 31 and size.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Field) {
            return false
        }

        return cells.contentEquals(other.cells) && size == other.size
    }

    fun shiftDown(lineIndex: Int) {
        (0 until lineIndex).reversed().forEach { y ->
            cells[y + 1] = cells[y]
        }
    }
}
