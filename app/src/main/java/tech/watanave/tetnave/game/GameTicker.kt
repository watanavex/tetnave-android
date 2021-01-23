package tech.watanave.tetnave.game

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameTickerImpl @Inject constructor() : GameTicker {

    override val tickFlow: Flow<Unit> = flow {
        while (true) {
            delay(300L)
            emit(Unit)
        }
    }

}
