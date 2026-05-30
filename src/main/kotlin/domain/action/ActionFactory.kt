package domain.action

import domain.card.CardEffect

object ActionFactory {

    fun createAction(
        effect: CardEffect,
        positions: List<Int>
    ): Action {
        return when(effect) {
            CardEffect.MOVE_UP_2 ->
                MoveUp2Action(
                    positions[0]
                )
            CardEffect.MOVE_DOWN_2 ->
                MoveDown2Action(
                    positions[0]
                )
            CardEffect.SWAP_NEIGHBORS ->
                SwapNeighborsAction(
                    positions[0]
                )
            CardEffect.SWAP_WITH_GAP ->
                SwapWithGapAction(
                    positions[0]
                )
            CardEffect.DESTROY_TOP ->
                DestroyTopAction()
            CardEffect.DESTROY_MIDDLE ->
                DestroyMiddleAction()
            CardEffect.DESTROY_BOTTOM ->
                DestroyBottomAction()
        }
    }
}