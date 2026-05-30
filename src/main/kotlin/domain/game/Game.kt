package domain.game

import domain.card.Card
import domain.deck.Deck
import domain.deck.DiscardPile
import domain.move.MoveHistory
import domain.player.Player

class Game(
    val players: List<Player>,
    val deck: Deck,
    val discardPile: DiscardPile
) {
    private var currentPlayerIndex = 0
    val currentPlayer: Player
        get() = players[currentPlayerIndex]
    val commonCards = mutableListOf<Card>()
    val moveHistory = mutableListOf<MoveHistory>()
    var isFinished = false
        private set
    var winner: Player? = null
        private set
    fun nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }
    fun finishGame(player: Player) {
        isFinished = true
        winner = player
    }
}