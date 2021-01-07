package tech.watanave.tetnave

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tech.watanave.tetnave.game.Game

class MainActivity : AppCompatActivity() {
    private val game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            game.start()
        }
        setContent {
            ActivityContent()
        }
    }

    @Preview
    @Composable
    private fun ActivityContent() {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "Tetnave")
                })
            }) {
            Log.w("@@@", "${ game.cells }")
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(0.05f))
                Box(modifier = Modifier
                    .weight(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .aspectRatio(0.5f)) {
                    Column {
                        (0 until game.size.height).forEach { y ->
                            Row {
                                (0 until game.size.width).forEach { x ->
                                    Log.w("@@@", Throwable().stackTrace.first().lineNumber.toString())
                                    val color = when (game.cells[y][x]) {
                                        Game.Cell.None -> Color.LightGray
                                        Game.Cell.Fixed -> Color.DarkGray
                                        Game.Cell.Temporary -> Color.Gray
                                    }
                                    Box(modifier = Modifier
                                        .aspectRatio(1f)
                                        .weight(1f)
                                        .border(1.0.dp, Color.White)
                                        .background(color))
                                }
                            }
                        }
                    }
                }
                ConstraintLayout(Modifier
                        .weight(0.20f)
                        .align(Alignment.CenterHorizontally)) {
                    val (rotateButton, leftButton, rightButton, downButton, anchor) = createRefs()
                    Spacer(modifier = Modifier
                            .size(25.dp)
                            .constrainAs(anchor) {
                                centerTo(parent)
                            })
                    Button(
                            onClick = { game.rotate() },
                            Modifier.height(50.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .constrainAs(rotateButton) {
                                        bottom.linkTo(anchor.top)
                                        centerHorizontallyTo(parent)
                                    }) {
                        Icon(Icons.Filled.Refresh)
                    }
                    Button(
                            onClick = { game.left() },
                            Modifier
                                    .height(50.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .constrainAs(leftButton) {
                                        centerVerticallyTo(parent)
                                        end.linkTo(anchor.start)
                                    }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                    Button(
                            onClick = { game.right() },
                            Modifier
                                    .height(50.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .constrainAs(rightButton) {
                                        centerVerticallyTo(parent)
                                        start.linkTo(anchor.end)
                                    }) {
                        Icon(Icons.Filled.ArrowForward)
                    }
                    Button(
                            onClick = { game.down() },
                            Modifier
                                    .height(50.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .constrainAs(downButton) {
                                        top.linkTo(anchor.bottom)
                                        centerHorizontallyTo(parent)
                                    }) {
                        Icon(Icons.Filled.ArrowDownward)
                    }
                }
            }
        }
    }
}