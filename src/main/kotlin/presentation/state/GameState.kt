package presentation.state

import domain.card.Card

import domain.move.MoveHistory

import domain.player.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val currentPlayer: Player? = null,
    val commonCards: List<Card> = emptyList(),
    val moveHistory: List<MoveHistory> = emptyList(),
    val isFinished: Boolean = false,
    val winner: Player? = null,
    val selectedPositions: List<Int> = emptyList(),
    val selectionMode: SelectionMode = SelectionMode.NONE
)