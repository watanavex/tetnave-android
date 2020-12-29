package tech.watanave.tetnave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivityContent()
        }
    }

    @Preview
    @Composable
    private fun ActivityContent() {
        Text("Android")
    }
}