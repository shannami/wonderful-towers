package presentation.screen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.view.CommonCardsView
import presentation.view.MoveHistoryView
import presentation.view.PlayerView
import presentation.view.StatisticsView
import presentation.viewmodel.GameViewModel
@Composable
fun GameScreen(
    viewModel: GameViewModel
) {
    val state = viewModel.state.value
    if (state.isFinished) {
        Column {
            Text(
                text = "Winner: ${state.winner?.name}"
            )
            Button(
                onClick = {
                    viewModel.restartGame()
                }
            ) {
                Text("Restart")
            }
        }
        return
    }
    Column {
        StatisticsView(
            currentPlayer = state.currentPlayer
        )
        Button(
            onClick = {
                viewModel.enableReplaceMode()
            }
        ) {
            Text("Replace Block")
        }
        CommonCardsView(
            cards = state.commonCards,
            onCardClick = { card ->
                viewModel.selectCommonCard(card)
            }
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
                Spacer(
                    modifier = Modifier.width(40.dp)
                )
            }
        }
        MoveHistoryView(
            history = state.moveHistory
        )
    }
}