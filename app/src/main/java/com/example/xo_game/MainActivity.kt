package com.example.xo_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xo_game.ui.theme.XOgameTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XOgameTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("XO GAME",
                                fontWeight = FontWeight.Bold) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFFA7D477),
                                titleContentColor = Color.Black
                            )
                        )
                    },
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        XOGame()
                    }
                }
            }
        }
    }
}

@Composable
fun XOGame() {
    val rows = 4
    val columns = 4
    var board by remember { mutableStateOf(List(rows * columns) {""}) }
    var currPlayer by remember { mutableStateOf("O") }
    val winner by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                repeat(columns) { columnIndex ->
                    val index = (rowIndex * columns) + columnIndex
                    XObtn(
                        gridSize = rows,
                        symbol = board[index],
                        enable = board[index].isEmpty() && winner.isNullOrEmpty(),
                        onClick = {
                            if(board[index].isEmpty()){
                                board = board.toMutableList().also { it[index] = currPlayer }
                                currPlayer = if (currPlayer == "O") "X" else "O"
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun XObtn(gridSize: Int, symbol: String, enable: Boolean, onClick:() -> Unit) {
    val boxSize = when(gridSize){
        4 -> 76.dp
        5 -> 68.dp
        6 -> 54.dp
        7 -> 46.dp
        else -> 40.dp
    }
    val fontSize = when(gridSize){
        4 -> 32.sp
        5 -> 28.sp
        6 -> 24.sp
        7 -> 20.sp
        else -> 16.sp
    }

    Box(
        modifier = Modifier
            .size(boxSize)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF72C5B))
            .clickable(enabled = enable, onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = symbol,
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XOgameTheme {
        XOGame()
    }
}