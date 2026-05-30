package domain.action

import domain.game.Game

interface Action {
    val displayName: String
    fun perform(game: Game)
}