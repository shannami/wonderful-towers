package domain.action

import domain.game.Game

class ReplaceBlockAction(
    val index: Int
) : Action {
    override val displayName = "Replace Block"
    override fun perform(game: Game) {
        val tower = game.currentPlayer.tower
        if (!tower.isValidIndex(index)) return
        val newCard = game.deck.draw() ?: return
        val oldCard = tower.removeAt(index) ?: return
        game.commonCards.add(oldCard)
        tower.insertAt(index, newCard)
    }
}