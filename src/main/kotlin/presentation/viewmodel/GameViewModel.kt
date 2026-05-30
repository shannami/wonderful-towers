package presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import domain.action.Action
import domain.action.ActionFactory
import domain.action.ReplaceBlockAction
import domain.card.Card
import domain.card.CardEffect
import presentation.state.GameState
import presentation.state.SelectionMode
import service.GameService
import util.GameFactory

class GameViewModel(
    private val gameService: GameService
) : ViewModel() {
    val state = mutableStateOf(GameState())
    private val selectedPositions = mutableListOf<Int>()
    private var selectedCommonCard: Card? = null
    private var selectionMode = SelectionMode.NONE

    init {
        updateState()
    }

    fun selectCommonCard(card: Card) {
        selectedPositions.clear()
        val required = getRequiredSelections(card.effect)
        if (required == 0) {
            performAction(ActionFactory.createAction(card.effect, emptyList()))
            gameService.useCommonCard(card)
            gameService.endTurn()
            resetSelection()
            return
        }
        selectedCommonCard = card
        selectionMode = SelectionMode.USE_COMMON_CARD
        updateState()
    }

    fun enableReplaceMode() {
        selectedPositions.clear()
        selectionMode = SelectionMode.REPLACE
        updateState()
    }

    fun selectPosition(position: Int) {
        if (selectionMode == SelectionMode.REPLACE) {
            performAction(ReplaceBlockAction(position))
            gameService.endTurn()
            resetSelection()
            return
        }
        selectedPositions.add(position)
        processSelection()
        updateState()
    }

    private fun processSelection() {
        val card = selectedCommonCard ?: return
        val required = getRequiredSelections(card.effect)
        if (selectedPositions.size < required) return
        performAction(ActionFactory.createAction(card.effect, selectedPositions))
        gameService.useCommonCard(card)
        gameService.endTurn()
        resetSelection()
    }

    private fun performAction(action: Action) {
        if (state.value.isFinished) return
        gameService.performAction(action)
        updateState()
    }

    private fun resetSelection() {
        selectedPositions.clear()
        selectedCommonCard = null
        selectionMode = SelectionMode.NONE
        updateState()
    }

    fun restartGame() {
        val newGame = GameFactory.createGame()
        gameService.setGame(newGame)
        resetSelection()
        updateState()
    }

    private fun updateState() {
        val game = gameService.getGame()
        state.value = GameState(
            players = game.players,
            currentPlayer = game.currentPlayer,
            commonCards = game.commonCards.toList(),
            moveHistory = game.moveHistory.toList(),
            isFinished = game.isFinished,
            winner = game.winner,
            selectedPositions = selectedPositions.toList(),
            selectionMode = selectionMode
        )
    }

    private fun getRequiredSelections(effect: CardEffect): Int {
        return when (effect) {
            CardEffect.MOVE_UP_2, CardEffect.MOVE_DOWN_2,
            CardEffect.SWAP_NEIGHBORS, CardEffect.SWAP_WITH_GAP -> 1
            CardEffect.DESTROY_TOP, CardEffect.DESTROY_MIDDLE, CardEffect.DESTROY_BOTTOM -> 0
        }
    }
}