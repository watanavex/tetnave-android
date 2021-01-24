package tech.watanave.tetnave.domain.value

typealias MapPattern = List<Pair<Int, Int>>

data class Block constructor(val origin: Position, val rotate: Rotate, val mapPattern: MapPattern) {

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
        ■ ■ ■
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
        /*
        ■
        ■
        ■ ■
        */
        val Pattern4 = listOf(
            (0 to -1),
            (0 to 1),
            (1 to 1)
        )
    }

    val positions: List<Position>
        get() = listOf(origin) + when (rotate) {
            Rotate.Top -> mapPattern.map { origin.move(it.first, it.second) }
            Rotate.Left -> mapPattern.map { origin.move(it.second, -it.first) }
            Rotate.Right -> mapPattern.map { origin.move(-it.second, it.first) }
            Rotate.Bottom -> mapPattern.map { origin.move(-it.first, -it.second) }
        }

    fun move(x: Int, y: Int) = copy(origin = origin.move(x, y))

    fun rotate(rotate: Rotate): Block {
        return copy(rotate = rotate)
    }

}
