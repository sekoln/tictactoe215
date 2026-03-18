package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
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
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*
import tictactoe.composeapp.generated.resources.Res

@Serializable
data class Game(
    val level1: String = "Easy AI",
    val level2: String = "Human",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
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
                                    contentDescription = stringResource(Res.string.arrow_left),
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
                    WelcomeScreen { level1, level2 ->
                        navController.navigate(Game(level1, level2))
                    }
                }
                composable<Game> { navBackStackEntry ->
                    val gameRound = navBackStackEntry.toRoute<Game>()
                    val level1 = gameRound.level1
                    val level2 = gameRound.level2
                    GameScreen(
                        level1,
                        level2,
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                    ) { player1Wins, player2Wins, ties ->
                        navController.navigate(GameOver(player1Wins, player2Wins, ties))
                    }
                }
                composable<GameOver> { navBackStackEntry ->
                    //val winner =
                    //val winnerNum =
                    GameOverScreen(
                        1, "Nora"
                    ) {level1, level2 ->
                        navController.navigate(Game(level1, level2))
                    }
                }
            }
        }
    }
}
