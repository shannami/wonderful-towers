package repository

import domain.game.Game

interface IGameRepository {
    fun save(
        game: Game
    )
    fun load(): Game?
}