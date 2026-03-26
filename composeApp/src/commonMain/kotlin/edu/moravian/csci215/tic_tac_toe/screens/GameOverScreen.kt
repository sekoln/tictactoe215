package edu.moravian.csci215.tic_tac_toe.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
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
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.announceTie
import tictactoe.composeapp.generated.resources.announceVictory
import tictactoe.composeapp.generated.resources.playAgain
import tictactoe.composeapp.generated.resources.tieMessage
import tictactoe.composeapp.generated.resources.victoryMessage
import tictactoe.composeapp.generated.resources.victoryStats

@Serializable
data class GameOver(
    val winnerNum: Int,
    val winnerName: String,
    val player1wins: Int,
    val player2wins: Int,
    val ties: Int,
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameOverScreen(
    winnerNum: Int,
    winnerName: String,
    gameOver: GameOver,
    startNewRound: () -> Unit
) {
    // header at the top with back arrow to game screen and "tic-tac-toe" title
    // player __ won!
    // Congrats winner
    // tally of each player wins and ties
    // start new game (bring to gamescreen)

    // store a var that  increases win count or tie


    //TODO: make tally work
    if (winnerNum == 0) {
        gameOver.ties + 1
    } else if (winnerNum == 1) {
        gameOver.player1wins + 1
    } else if (winnerNum == 2) {
        gameOver.player2wins + 1
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
            text = if (winnerNum == 0)  {
                stringResource(Res.string.announceTie)
            } else {
                stringResource(Res.string.announceVictory, winnerNum)
            },
        )
        Text(
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            text = if (winnerNum == 0) {
                stringResource(Res.string.tieMessage)
            } else {
                stringResource(Res.string.victoryMessage, winnerName)
            },
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Text(
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            text = stringResource(Res.string.victoryStats, gameOver.player1wins, gameOver.player2wins, gameOver.ties),
        )
        ElevatedButton(
            modifier = Modifier.size(width = 200.dp, height = 100.dp),
            onClick = { startNewRound() },
        ) {
            Text(
                text = stringResource(Res.string.playAgain),
                fontSize = 20.sp
            )
        }
    }
}
