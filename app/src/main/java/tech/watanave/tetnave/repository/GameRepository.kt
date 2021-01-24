package tech.watanave.tetnave.repository

import kotlinx.coroutines.flow.*
import tech.watanave.tetnave.domain.GameRepository
import tech.watanave.tetnave.domain.entity.Field
import tech.watanave.tetnave.domain.value.Block
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(): GameRepository {

    private val _blockFlow = MutableStateFlow<Block?>(null)
    override val blockFlow = _blockFlow.asStateFlow()

    private val _fieldFlow = MutableStateFlow<Field?>(null)
    override val fieldFlow = _fieldFlow.asStateFlow()

    override fun getBlock() : Block? = _blockFlow.value
    override fun getField() : Field? = _fieldFlow.value

    override fun updateBlock(block: Block?) {
        _blockFlow.value = block
    }
    override fun updateField(field: Field) {
        _fieldFlow.value = field
    }

}