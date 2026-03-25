package edu.moravian.csci215.tic_tac_toe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.arrow_left
import tictactoe.composeapp.generated.resources.easyAIType
import tictactoe.composeapp.generated.resources.hardAIType
import tictactoe.composeapp.generated.resources.humanType
import tictactoe.composeapp.generated.resources.mediumAIType
import tictactoe.composeapp.generated.resources.playerLabel
import tictactoe.composeapp.generated.resources.playerTypeSelection
import tictactoe.composeapp.generated.resources.start
import tictactoe.composeapp.generated.resources.welcomeTextFieldPrompt
import tictactoe.composeapp.generated.resources.welcome_to_app

@Serializable
data object Welcome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    showSnackbar: (String) -> Unit,
    startGame: (player1Type: String, player2Type: String, player1Name: String, player2Name: String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        var player1Type by remember { mutableStateOf("") }
        var player1Name by remember { mutableStateOf("") }
        var player2Type by remember { mutableStateOf("") }
        var player2Name by remember { mutableStateOf("") }
        Row {
            Row {
                //app logo
                Icon(
                    painter = painterResource(Res.drawable.ic_launcher_foreground),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
            }
            Row {
                Text(
                    text = stringResource(Res.string.welcome_to_app),
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .safeContentPadding(),
                    lineHeight = 60.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .safeContentPadding(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(190.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                playerSideSetUp(playerNum = 1, selectedType = player1Type,
                    onTypeChange = { player1Type = it },
                    name = player1Name,
                    onNameChange = { player1Name = it })
            }
            Column(
                modifier = Modifier.width(190.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                playerSideSetUp(playerNum = 2,
                    selectedType = player2Type,
                    onTypeChange = { player2Type = it },
                    name = player2Name,
                    onNameChange = { player2Name = it })
            }
        }
        Row {

            val filledInAll = player1Type.isNotBlank() && player1Name.isNotBlank() && player2Type.isNotBlank() && player2Name.isNotBlank()

            Button(
                //TODO: placeholder values!!! also the snackbar goes here
                onClick = {
                    if (filledInAll) {
                    startGame(player1Type, player2Type, player1Name, player2Name)
                    } else {
                        showSnackbar("Missing player information!")
                    }
                          },
                modifier = Modifier
                    .height(75.dp)
                    .width(125.dp)
            ) {
                Text(
                    stringResource(Res.string.start),
                    fontSize = 25.sp
                )
            }
        }
    }
}

/**
 * Creates the player type and name selection
 */
@Composable
fun playerSideSetUp(
    playerNum: Int,
    selectedType: String,
    onTypeChange: (String) -> Unit,
    name: String,
    onNameChange: (String) -> Unit
)
{
    Row {
        Text(stringResource(Res.string.playerLabel, playerNum))
    }
    Row {
        val option = selectedType
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp)
            ) {
                Text(
                    text = option.ifEmpty { stringResource(Res.string.playerTypeSelection) },
                    fontSize = 12.sp
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                val humanTypeString = stringResource(Res.string.humanType)
                val easyAIString = stringResource(Res.string.easyAIType)
                val mediumAIString = stringResource(Res.string.mediumAIType)
                val hardAIString = stringResource(Res.string.hardAIType)

                DropdownMenuItem(
                    text = { Text(humanTypeString) },
                    onClick = {
                        onTypeChange(humanTypeString)
                        expanded = false
                    },
                )
                DropdownMenuItem(
                    text = { Text(easyAIString) },
                    onClick = {
                        onTypeChange(easyAIString)
                        expanded = false
                    },
                )
                DropdownMenuItem(
                    text = { Text(mediumAIString) },
                    onClick = {
                        onTypeChange(mediumAIString)
                        expanded = false
                    },
                )
                DropdownMenuItem(
                    text = { Text(hardAIString) },
                    onClick = {
                        onTypeChange(hardAIString)
                        expanded = false
                    },
                )
            }
        }
    }
    Row {
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(Res.string.welcomeTextFieldPrompt, playerNum)) },
            singleLine = true,
            modifier = Modifier
                .height(50.dp)
                .width(120.dp),
        )
    }
}