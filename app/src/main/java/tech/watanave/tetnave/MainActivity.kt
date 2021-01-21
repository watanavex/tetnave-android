package tech.watanave.tetnave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import tech.watanave.tetnave.game.Game

class MainActivity : AppCompatActivity() {

    val game: Game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivityContent()
        }

        lifecycleScope.launchWhenCreated {
            game.start()
        }

        lifecycleScope.launchWhenStarted {
            game.counter.collect { count ->
                Log.w("@@@", "counter = $count")
            }
            Log.w("@@@", "done")
        }
        lifecycleScope.launchWhenStarted {
            game.list.collect { list ->
                Log.w("@@@", "list = $list")
            }
            Log.w("@@@", "done")
        }
        lifecycleScope.launchWhenStarted {
            game.unit.collect { list ->
                Log.w("@@@", "unit")
            }
            Log.w("@@@", "done")
        }

        lifecycleScope.launchWhenStarted {
            game.cell.collectLatest {
                val count = it.flatMap { it }
                        .count { it != Game.Cell.Fixed }
                Log.w("@@@", "$$$$ count = $count")
            }
            Log.w("@@@", "done")
        }
    }

    @Preview
    @Composable
    private fun ActivityContent() {
        Text("Android")
    }
}