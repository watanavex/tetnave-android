package tech.watanave.tetnave.service

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameTickerImpl @Inject constructor() : GameTicker {

    override val tickFlow: Flow<Unit> = flow {
        while (true) {
            Log.w("@@@", "tick")
            emit(Unit)
            delay(300L)
        }
    }

}
