package tech.watanave.tetnave.game

import javax.inject.Inject

class GameSpecificationImpl @Inject constructor() : GameSpecification {

    override fun isSatisfied(block: Block, field: Field) : Boolean {
        block.positions.forEach { position ->
            if (!isSatisfied(position, field)) {
                return false
            }
        }

        return true
    }

    override fun isSatisfied(position: Position, field: Field) : Boolean {
        if (position.x < 0 || position.y < 0 || position.x >= field.size.width || position.y >= field.size.height) {
            return false
        }
        if (field[position] != Cell.None) {
            return false
        }
        return true
    }

}