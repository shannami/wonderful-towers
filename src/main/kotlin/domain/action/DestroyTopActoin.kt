package domain.action

import domain.game.Game

class DestroyTopAction : Action {
    override val displayName = "Destroy Top"
    override fun perform(game: Game) {
        game.players.forEach { player ->
            if (player.tower.isEmpty()) return@forEach
            val newCard = game.deck.draw() ?: return@forEach
            val index = 0
            val removed = player.tower.removeAt(index) ?: return@forEach
            game.commonCards.add(removed)
            player.tower.insertAt(index, newCard)
        }
    }
}