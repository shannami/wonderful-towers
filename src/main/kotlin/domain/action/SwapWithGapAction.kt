package domain.action

import domain.game.Game

class SwapWithGapAction(
    val index: Int
) : Action {
    override val displayName = "Swap With Gap"
    override fun perform(
        game: Game
    ) {
        game.currentPlayer.tower.swap(index, index + 2)
    }

}