package presentation.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import domain.player.Player
@Composable

fun StatisticsView(

    currentPlayer: Player?
) {
    Text(
        text =
            "Current Player: ${currentPlayer?.name}"
    )
}