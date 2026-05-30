package domain.action

import domain.game.Game

class MoveUp2Action(
    val index: Int
) : Action {
    override val displayName = "Move Up 2"
    override fun perform(game: Game) {
        val tower = game.currentPlayer.tower
        if (!tower.isValidIndex(index)) return
        val newIndex = index - 2
        if (newIndex < 0) return
        val card = tower.removeAt(index) ?: return
        tower.insertAt(newIndex, card)
    }
}