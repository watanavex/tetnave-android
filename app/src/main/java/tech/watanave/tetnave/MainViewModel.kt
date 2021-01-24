package tech.watanave.tetnave

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.watanave.tetnave.service.GameService

class MainViewModel @ViewModelInject constructor(val gameService: GameService) : ViewModel() {

    fun gameStart() {
        viewModelScope.launch(Dispatchers.IO) {
            gameService.start()
        }
    }

    fun left() {
        gameService.left()
    }

    fun right() {
        gameService.right()
    }

    fun down() {
        gameService.down()
    }

    fun rotate() {
        gameService.rotate()
    }

}