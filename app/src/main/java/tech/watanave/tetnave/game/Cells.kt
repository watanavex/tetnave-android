package tech.watanave.tetnave.game

interface FieldCell
interface BlockCell

sealed class Cell {
    object Fixed: Cell(), FieldCell
    object None: Cell(), FieldCell
    object Moving: Cell(), BlockCell
}
