package edu.moravian.csci215.tic_tac_toe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.welcome_to_app

@Composable
fun GameScreen(
    player1Type: String,
    player2Type: String,
    showSnackbar: (String) -> Unit,
    navigateToGameOver: (player1Wins: Int, player2Wins: Int, ties: Int) -> Unit,
) {
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.SpaceBetween,
           modifier = Modifier
               .background(MaterialTheme.colorScheme.primaryContainer)
               .safeContentPadding()
               .fillMaxSize(),
       ) {
           Row {
               Row {
                   //app logo
               }
               Row {
                   Text(
                       //placeholder text value
                       text = "HELLO WE DID IT",
                       fontSize = 50.sp,
                       textAlign = TextAlign.Center,
                       modifier = Modifier
                           .safeContentPadding(),
                       lineHeight = 60.sp
                   )
               }
           }
           Button(
                   onClick = { navigateToGameOver(17, 12, 2700) },
                   modifier = Modifier
                       .safeContentPadding()
               ) {
                    //this will not be here, it will just happen
                   Text(text = "Go to Game Over")
               }
       }
}
