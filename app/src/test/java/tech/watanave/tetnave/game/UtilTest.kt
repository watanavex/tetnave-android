package tech.watanave.tetnave.game

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.watanave.tetnave.domain.value.Position

class UtilTest {

    @Test
    fun move() {
        val position = Position(3, 6)
            .move(2, -1)

        assertThat(position).isEqualTo(Position(5, 5))
    }

}
