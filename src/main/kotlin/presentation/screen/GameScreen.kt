package presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.view.CommonCardsView
import presentation.view.MoveHistoryView
import presentation.view.PlayerView
import presentation.view.StatisticsView
import presentation.viewmodel.GameViewModel
import repository.IGameRepository

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    repository: IGameRepository
) {
    val state = viewModel.state.value
    var showStats by remember { mutableStateOf(false) }

    if (showStats) {
        StatisticsScreen(repository, onBack = { showStats = false })
        return
    }

    if (state.isFinished) {
        Column {
            Text(text = "Winner: ${state.winner?.name}")
            Button(onClick = { viewModel.restartGame() }) {
                Text("Restart")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { showStats = true }) {
                Text("Statistics")
            }
        }
        return
    }

    Column {
        StatisticsView(currentPlayer = state.currentPlayer)
        Button(onClick = { viewModel.enableReplaceMode() }) {
            Text("Replace Block")
        }
        Button(onClick = { showStats = true }) {
            Text("Statistics")
        }
        CommonCardsView(
            cards = state.commonCards,
            onCardClick = { card -> viewModel.selectCommonCard(card) }
        )
        Row {
            state.players.forEach { player ->
                PlayerView(
                    player = player,
                    isCurrentPlayer = player == state.currentPlayer,
                    onCardClick = { index ->
                        if (player == state.currentPlayer) {
                            viewModel.selectPosition(index)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(40.dp))
            }
        }
        MoveHistoryView(history = state.moveHistory)
    }
}