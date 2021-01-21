package tech.watanave.tetnave.game

data class Position(val x: Int, val y: Int) {
    fun move(x: Int, y: Int) : Position {
        return Position(this.x + x, this.y + y)
    }
}
data class Size(val width: Int, val height: Int)