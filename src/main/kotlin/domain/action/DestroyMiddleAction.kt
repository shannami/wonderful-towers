package domain.action

import domain.game.Game

class DestroyMiddleAction : Action {
    override val displayName = "Destroy Middle"
    override fun perform(game: Game) {
        game.players.forEach { player ->
            if (player.tower.isEmpty()) return@forEach
            val newCard = game.deck.draw() ?: return@forEach
            val index = player.tower.size() / 2
            val removed = player.tower.removeAt(index) ?: return@forEach
            game.commonCards.add(removed)
            player.tower.insertAt(index, newCard)
        }
    }
}