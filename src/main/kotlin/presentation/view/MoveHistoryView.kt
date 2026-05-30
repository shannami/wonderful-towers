package presentation.view

import androidx.compose.foundation.layout.Column

import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import domain.move.MoveHistory

@Composable

fun MoveHistoryView(
    history: List<MoveHistory>
) {
    Column {
        history.forEach { move ->
            Text(
                text = "${move.playerName}: ${move.actionName}"
            )
        }
    }
}