package validation

import domain.action.*
import domain.game.Game

class MoveValidator {
    fun validate(game: Game, action: Action): Boolean {
        val tower = game.currentPlayer.tower
        return when (action) {
            is ReplaceBlockAction -> tower.isValidIndex(action.index)
            is MoveUp2Action -> action.index >= 2 && tower.isValidIndex(action.index)
            is MoveDown2Action -> action.index <= tower.lastIndex() - 2
            is SwapNeighborsAction -> action.index in 0 until tower.lastIndex()
            is SwapWithGapAction -> action.index <= tower.lastIndex() - 2
            is DestroyTopAction, is DestroyMiddleAction, is DestroyBottomAction -> !tower.isEmpty()
            else -> false
        }
    }
}