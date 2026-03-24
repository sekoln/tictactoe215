package edu.moravian.csci215.tic_tac_toe.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.announceTurn
import tictactoe.composeapp.generated.resources.illegalMove
import tictactoe.composeapp.generated.resources.welcome_to_app

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameScreen(
    player1Type: String,
    player2Type: String,
    player1Name: String,
    player2Name: String,
    showSnackbar: (String) -> Unit,
    navigateToGameOver: (GameOver) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        var playerNum by remember { mutableIntStateOf(0) }

        Text (
            text = stringResource(Res.string.announceTurn, playerNum)
        )
        Row {
            createBoard(showSnackbar)
        }
        Button(
            onClick = { navigateToGameOver(
                GameOver(player1Type, player2Type, player1Name, player2Name)
            ) },
            modifier = Modifier
                .safeContentPadding()
        ) {
            //TODO this will not be here, it will just happen
            Text(text = "Go to Game Over")
        }
    }
}

@Composable
fun createBoard(
    showSnackbar: (String) -> Unit) {
    Column {
        repeat(3) { rowIndex ->
            Row {
                repeat(3) { columnIndex ->
                    val invalidMoveMessage = stringResource(Res.string.illegalMove)
                    var isFilled by remember { mutableStateOf(false) }
                    if (isFilled) {
                        //swap these two
                        Button (
                            onClick = {showSnackbar(invalidMoveMessage)},
                            modifier = Modifier
                                //.width(200.dp)
                                //.height(100.dp)
                                .padding(8.dp)
                                .border(2.dp, Color.Black)
                        ) {
                            Text(text = "Move made")
                        }
                    } else {
                        TextButton(
                            onClick = {isFilled = true},
                            modifier = Modifier
                                //.width(200.dp)
                                //.height(100.dp)
                                .padding(8.dp)
                                .border(2.dp, Color.Black)
                        ) {
                            Text(text = "No move yet")
                        }
                    }
                }
            }
        }
    }
}