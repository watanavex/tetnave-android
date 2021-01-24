package tech.watanave.tetnave.game

import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(): GameRepository {

    private val _blockFlow = MutableSharedFlow<Block?>(replay = 1)
    override val blockFlow = _blockFlow.asSharedFlow()

    private val _fieldFlow = MutableSharedFlow<Field>(replay = 1)
    override val fieldFlow = _fieldFlow.asSharedFlow()

    override suspend fun getBlock() : Block? = blockFlow.firstOrNull()
    override suspend fun getField() : Field? = fieldFlow.firstOrNull()

    override suspend fun updateBlock(block: Block?) {
        _blockFlow.emit(block)
    }
    override suspend fun updateField(field: Field) {
        _fieldFlow.emit(field)
    }

}