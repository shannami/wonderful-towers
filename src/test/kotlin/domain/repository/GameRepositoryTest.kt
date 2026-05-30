package repository

import domain.game.Game
import domain.move.MoveHistory
import domain.player.Player
import domain.tower.Tower
import domain.deck.Deck
import domain.deck.DiscardPile
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GameRepositoryTest {

    private fun createFreshRepository(): GameRepository {
        return GameRepository("jdbc:sqlite::memory:")
    }

    @Test
    fun `save game saves to database`() {
        val repo = createFreshRepository()
        val game = createFinishedGame(winnerName = "Player 1")
        repo.save(game)

        val games = repo.getAllGames()
        assertEquals(1, games.size)
        assertEquals("Player 1", games[0].winnerName)
        assertEquals(1, games[0].totalMoves)
    }

    @Test
    fun `player stats aggregated correctly`() {
        val repo = createFreshRepository()
        val game1 = createFinishedGame(winnerName = "Player 1")
        val game2 = createFinishedGame(winnerName = "Player 2")

        repo.save(game1)
        repo.save(game2)

        val stats = repo.getPlayerStats()
        val p1 = stats.find { it.name == "Player 1" }
        val p2 = stats.find { it.name == "Player 2" }
        assertNotNull(p1)
        assertNotNull(p2)
        assertEquals(2, p1?.gamesPlayed)
        assertEquals(1, p1?.gamesWon)
        assertEquals(2, p2?.gamesPlayed)
        assertEquals(1, p2?.gamesWon)
    }

    private fun createFinishedGame(winnerName: String = "Player 1"): Game {
        val player1 = Player("Player 1", Tower())
        val player2 = Player("Player 2", Tower())
        val game = Game(
            players = listOf(player1, player2),
            deck = Deck(mutableListOf()),
            discardPile = DiscardPile()
        )
        game.moveHistory.add(MoveHistory(winnerName, "Test Action"))
        val winner = if (winnerName == "Player 1") player1 else player2
        game.finishGame(winner)
        return game
    }
}