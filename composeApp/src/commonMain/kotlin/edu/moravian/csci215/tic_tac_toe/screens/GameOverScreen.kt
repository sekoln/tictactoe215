package edu.moravian.csci215.tic_tac_toe.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable

@Serializable
data class GameOver(
    val player1wins: Int,
    val player2wins: Int,
    val ties: Int,
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameOverScreen(
    winnerNum: Int,
    winnerName: String,
    startNewRound: (level1: String, level2: String) -> Unit
) {
    var player1wins by remember { mutableIntStateOf(0) }
    var player2wins by remember { mutableIntStateOf(0) }
    var ties by remember { mutableIntStateOf(0) }
    // header at the top with back arrow to game screen and "tic-tac-toe" title
    // player __ won!
    // Congrats winner
    // tally of each player wins and ties
    // start new game (bring to gamescreen)

    // store a var that  increases win count or tie

    if (winnerNum == 0) {
        ties++
    } else if (winnerNum == 1) {
        player1wins++
    } else if (winnerNum == 2) {
        player2wins++
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        Text(
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            text = if (winnerNum == 0) "It was a tie\uD83E\uDD7A" else "🎉🎉🎉Player $winnerNum won!🎉🎉🎉",
        )
        Text(
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            text = if (winnerNum == 0) "I do not know how to tie a tie 😔" else "Congrats, $winnerName!🥳",
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Text(
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            text = "Player 1 Wins: $player1wins\nPlayer 2 Wins: $player2wins\nTies: $ties",
        )
        ElevatedButton(
            modifier = Modifier.size(width = 200.dp, height = 100.dp),
            onClick = { startNewRound("Human", "Easy AI")},
        ) {
            Text("Start New Game")
        }
    }
}
