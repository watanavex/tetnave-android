package tech.watanave.tetnave.game

typealias MapPattern = List<Pair<Int, Int>>

data class Block(private val origin: Position, private val rotate: Rotate, private val mapPattern: MapPattern) {

    enum class Rotate {
        Top, Right, Bottom, Left
    }

    companion object {
        /*
        ■ ■
        ■ ■
         */
        val Pattern1 = listOf(
            (1 to 0),
            (0 to 1),
            (1 to 1)
        )
        /*
          ■
        ■ ■
          ■
        */
        val Pattern2 = listOf(
            (0 to -1),
            (-1 to 0),
            (1 to 0)
        )
        /*
        ■
        ■ ■
          ■
        */
        val Pattern3 = listOf(
            (0 to -1),
            (1 to 0),
            (1 to 1)
        )
    }

    val positions: List<Position>
        get() = listOf(origin) + when (rotate) {
            Rotate.Top -> mapPattern.map { origin.move(it.first, it.second) }
            Rotate.Left -> mapPattern.map { origin.move(it.second, it.first) }
            Rotate.Right -> mapPattern.map { origin.move(-it.second, it.first) }
            Rotate.Bottom -> mapPattern.map { origin.move(it.first, -it.second) }
        }

    fun move(x: Int, y: Int) = copy(origin = origin.move(x, y))

    fun rotate(): Block {
        var ordinal = rotate.ordinal + 1
        if (Rotate.values().size <= ordinal) {
            ordinal = 0
        }
        val rotate = Rotate.values()[ordinal]
        return copy(rotate = rotate)
    }

}
