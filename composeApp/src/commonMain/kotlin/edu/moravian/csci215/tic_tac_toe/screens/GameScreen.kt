package edu.moravian.csci215.tic_tac_toe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.moravian.csci215.tic_tac_toe.game.Board
import edu.moravian.csci215.tic_tac_toe.game.EasyAIPlayer
import edu.moravian.csci215.tic_tac_toe.game.HardAIPlayer
import edu.moravian.csci215.tic_tac_toe.game.HumanPlayer
import edu.moravian.csci215.tic_tac_toe.game.MediumAIPlayer
import edu.moravian.csci215.tic_tac_toe.game.Player
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*
import tictactoe.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameScreen(
    player1Type: String,
    player2Type: String,
    player1Name: String,
    player2Name: String,
    showSnackbar: (String) -> Unit,
    navigateToGameOver: (Int, String, Int, Int, Int) -> Unit,
) {
    var board by remember { mutableStateOf(Board()) }
    var player1Wins by remember { mutableIntStateOf(0) }
    var player2Wins by remember { mutableIntStateOf(0) }
    var ties by remember { mutableIntStateOf(0) }
    var winnerNum by remember { mutableIntStateOf(0) }
    var winnerName by remember { mutableStateOf("") }

    LaunchedEffect(board) {
        if (board.isGameOver) {
            if (board.hasWon('X')) {
                winnerNum = 1
                winnerName = player1Name
                player1Wins++
            } else if (board.hasWon('O')) {
                winnerNum = 2
                winnerName = player2Name
                player2Wins++
            } else if (board.hasTied) {
                winnerNum = 0
                winnerName = ""
                ties++
            }
            navigateToGameOver(winnerNum, winnerName, player1Wins, player2Wins, ties)
            return@LaunchedEffect
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        var playerNum by remember { mutableIntStateOf(0) }
        val player1Difficulty = convertToPlayer(player1Type)
        val player2Difficulty = convertToPlayer(player2Type)

        Text(
            text = stringResource(Res.string.announceTurn, playerNum),
            fontSize = 30.sp,
        )
        Row {
            CreateBoard(showSnackbar, board, { r, c ->
                val newBoard = board.playPiece(r, c)
                if (newBoard != null) {
                    board = newBoard
                }
            })
        }
    }
}

@Composable
fun convertToPlayer(playerString: String): Player {
    when (playerString) {
        stringResource(Res.string.humanType) -> return HumanPlayer()
        stringResource(Res.string.easyAIType) -> return EasyAIPlayer()
        stringResource(Res.string.mediumAIType) -> return MediumAIPlayer()
        stringResource(Res.string.hardAIType) -> return HardAIPlayer()
    }
    return HumanPlayer()
}

@Composable
fun CreateBoard(
    showSnackbar: (String) -> Unit,
    board: Board,
    onClick: (Int, Int) -> Unit,
) {
    Column {
        repeat(3) { rowIndex ->

            Row {
                repeat(3) { columnIndex ->

                    val invalidMoveMessage = stringResource(Res.string.illegalMove)
                    var isFilled by remember { mutableStateOf(false) }

                    if (isFilled) {
                        Button(
                            onClick = { showSnackbar(invalidMoveMessage) },
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .padding(8.dp)
                                .border(2.dp, Color.Black),
                        ) {
                            Text(
                                text = board.get(rowIndex, columnIndex).toString(),
                                fontSize = 50.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        TextButton(
                            onClick = {
                                onClick(rowIndex, columnIndex)
                                isFilled = true
                            },
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .padding(8.dp)
                                .border(2.dp, Color.Black),
                        ) {
                            Text(text = "")
                        }
                    }
                }
            }
        }
    }
}
