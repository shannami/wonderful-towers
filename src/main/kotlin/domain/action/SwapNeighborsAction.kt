package domain.action

import domain.game.Game

class SwapNeighborsAction(
    val index: Int
) : Action {
    override val displayName = "Swap Neighbors"
    override fun perform(game: Game) {
        val tower = game.currentPlayer.tower
        if (tower.isValidIndex(index) && tower.isValidIndex(index + 1))
            tower.swap(index, index + 1)
    }

}