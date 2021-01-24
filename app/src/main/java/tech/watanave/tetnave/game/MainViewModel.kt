package tech.watanave.tetnave.game

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(val gameService: GameService) : ViewModel() {

    fun gameStart() {
        viewModelScope.launch {
            gameService.start()
        }
    }

}