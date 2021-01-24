package tech.watanave.tetnave.domain.value

interface FieldCell
interface BlockCell

sealed class Cell {
    object Fixed: Cell(), FieldCell
    object None: Cell(), FieldCell
    object Moving: Cell(), BlockCell
}
