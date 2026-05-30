package presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import domain.player.Player

@Composable
fun PlayerView(
    player: Player,
    isCurrentPlayer: Boolean,
    onCardClick: (Int) -> Unit
) {
    Column {
        Text(
            text = player.name,
            color = if (isCurrentPlayer) Color.Green else Color.Black
        )
        TowerView(
            cards = player.tower.toList(),
            onCardClick = onCardClick
        )
    }
}