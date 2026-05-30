package service

import domain.action.Action
import domain.card.Card
import domain.game.Game
import domain.move.MoveHistory
import repository.IGameRepository
import validation.MoveValidator

class GameService(
    private var game: Game,
    private val repository: IGameRepository,
    private val validator: MoveValidator
) {

    fun performAction(action: Action) {
        if (!validator.validate(game, action)) return
        action.perform(game)
        game.moveHistory.add(
            MoveHistory(
                playerName = game.currentPlayer.name,
                actionName = action.displayName
            )
        )
        handleDuplicateEffects()
        checkWin()
    }

    fun useCommonCard(card: Card) {
        game.commonCards.removeIf { it.id == card.id }
        game.discardPile.add(card)
    }

    fun endTurn() {
        game.nextPlayer()
    }

    fun getGame(): Game = game

    fun setGame(newGame: Game) {
        game = newGame
    }

    private fun handleDuplicateEffects() {
        val grouped = game.commonCards.groupBy { it.effect }
        grouped.values.forEach { list ->
            if (list.size >= 2) {
                val effect = list.first().effect
                game.commonCards.removeAll { it.effect == effect }
                game.discardPile.cards.addAll(list)
            }
        }
    }

    private fun checkWin() {
        if (game.isFinished) return
        game.players.forEach { player ->
            if (player.tower.isSorted()) {
                game.finishGame(player)
                repository.save(game)
            }
        }
    }
}