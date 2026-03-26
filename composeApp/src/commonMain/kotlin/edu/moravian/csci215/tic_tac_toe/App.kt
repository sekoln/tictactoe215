package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.moravian.csci215.tic_tac_toe.screens.GameOver
import edu.moravian.csci215.tic_tac_toe.screens.GameOverScreen
import edu.moravian.csci215.tic_tac_toe.screens.GameScreen
import edu.moravian.csci215.tic_tac_toe.screens.Welcome
import edu.moravian.csci215.tic_tac_toe.screens.WelcomeScreen
import edu.moravian.csci215.tic_tac_toe.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*
import tictactoe.composeapp.generated.resources.Res

@Serializable
data class Game(
    val player1Type: String,
    val player2Type: String,
    val player1Name: String,
    val player2Name: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var player1Wins by remember { mutableIntStateOf(0) }
    var player2Wins by remember { mutableIntStateOf(0) }
    var ties by remember { mutableIntStateOf(0) }
    AppTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val navController = rememberNavController()
        // AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination
                if (currentDestination?.hasRoute<Welcome>() != true) {
                    TopAppBar(
                        title = { Text(stringResource(Res.string.app_name)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.navigateUp() },
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.arrow_left),
                                    tint = Color.White,
                                    contentDescription = stringResource(Res.string.arrow_left),
                                    modifier = Modifier
                                        .size(35.dp)
                                )
                            }
                        },
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Welcome,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable<Welcome> {
                    WelcomeScreen(
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    ) { player1Type, player2Type, player1Name, player2Name ->
                        navController.navigate(Game(player1Type, player2Type, player1Name, player2Name))
                    }
                }
                composable<Game> { navBackStackEntry ->
                    val gameRound = navBackStackEntry.toRoute<Game>()
                    val player1Type = gameRound.player1Type
                    val player2Type = gameRound.player2Type
                    val player1Name = gameRound.player1Name
                    val player2Name = gameRound.player2Name
                    GameScreen(
                        player1Type,
                        player2Type,
                        player1Name,
                        player2Name,
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                    ) { winnerNum: Int, winnerName: String, _, _, _ ->
                        when (winnerNum) {
                            0 -> ties++
                            1 -> player1Wins++
                            2 -> player2Wins++
                        }
                        navController.navigate(GameOver(winnerNum, winnerName, player1Wins, player2Wins, ties))
                    }
                }
                composable<GameOver> { navBackStackEntry ->
                    val gameOver = navBackStackEntry.toRoute<GameOver>()
                    GameOverScreen(gameOver.winnerNum, gameOver.winnerName, gameOver
                    ) { navController.navigateUp() }
                }
            }
        }
    }
}