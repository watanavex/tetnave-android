package tech.watanave.tetnave.game

import com.google.common.truth.Truth
import org.junit.Test
import tech.watanave.tetnave.game.Block.Companion.Pattern1
import tech.watanave.tetnave.game.Block.Companion.Pattern3
import tech.watanave.tetnave.game.Block.Companion.Pattern4

class BlockTest {

    @Test
    fun pattern1() {
        val block = Block(
            origin = Position(0, 0),
            rotate = Block.Rotate.Top,
            mapPattern = Pattern1
        )

        printBlock(block)
        Truth.assertThat(block.positions)
            .containsExactly(
                Position(0, 0),
                Position(1, 0),
                Position(0, 1),
                Position(1, 1),
            )
    }

    @Test
    fun pattern3() {
        val blockTop = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Top,
            mapPattern = Pattern3
        )
        Truth.assertThat(blockTop.positions)
            .containsExactly(
                Position(2, 1),
                Position(2, 0),
                Position(3, 1),
                Position(3, 2),
            )

        val blockLeft = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Left,
            mapPattern = Pattern3
        )
        Truth.assertThat(blockLeft.positions)
            .containsExactly(
                Position(2, 1),
                Position(1, 1),
                Position(2, 0),
                Position(3, 0),
            )

        val blockBottom = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Bottom,
            mapPattern = Pattern3
        )
        Truth.assertThat(blockBottom.positions)
            .containsExactly(
                Position(2, 1),
                Position(1, 1),
                Position(2, 2),
                Position(1, 0),
            )

        val blockRight = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Right,
            mapPattern = Pattern3
        )
        Truth.assertThat(blockRight.positions)
            .containsExactly(
                Position(2, 1),
                Position(3, 1),
                Position(2, 2),
                Position(1, 2),
            )

        printBlock(blockTop)
        printBlock(blockLeft)
        printBlock(blockBottom)
        printBlock(blockRight)
    }

    @Test
    fun pattern4() {
        val blockTop = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Top,
            mapPattern = Pattern4
        )
        Truth.assertThat(blockTop.positions)
            .containsExactly(
                Position(2, 1),
                Position(2, 0),
                Position(2, 2),
                Position(3, 2),
            )

        val blockLeft = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Left,
            mapPattern = Pattern4
        )
        Truth.assertThat(blockLeft.positions)
            .containsExactly(
                Position(2, 1),
                Position(1, 1),
                Position(3, 1),
                Position(3, 0),
            )

        val blockBottom = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Bottom,
            mapPattern = Pattern4
        )
        Truth.assertThat(blockBottom.positions)
            .containsExactly(
                Position(2, 1),
                Position(2, 2),
                Position(2, 0),
                Position(1, 0),
            )

        val blockRight = Block(
            origin = Position(2, 1),
            rotate = Block.Rotate.Right,
            mapPattern = Pattern4
        )
        Truth.assertThat(blockRight.positions)
            .containsExactly(
                Position(2, 1),
                Position(3, 1),
                Position(1, 1),
                Position(1, 2),
            )

        printBlock(blockTop)
        printBlock(blockLeft)
        printBlock(blockBottom)
        printBlock(blockRight)
    }

    private fun printBlock(block: Block) {
        val field = (0 until 5).map {
            (0 until 5).map { 0 }.toMutableList()
        }.toMutableList()

        block.positions.forEach { position ->
            field[position.y][position.x] = 1
        }

        println("- - - - - - ")
        field.forEach { column ->
            column.forEach {
                if (it == 0) {
                    print("□ ")
                } else {
                    print("■ ")
                }
            }
            print("\n")
        }
        println("- - - - - - ")
    }
}