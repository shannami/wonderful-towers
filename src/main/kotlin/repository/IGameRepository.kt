package repository

import domain.game.Game

interface IGameRepository {
    fun save(game: Game)
    fun load(): Game?
    fun getPlayerStats(): List<PlayerStat>
    fun getAllGames(): List<GameRecord>
}