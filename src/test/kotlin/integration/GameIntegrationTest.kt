package integration

import domain.action.SwapNeighborsAction
import domain.card.Card
import domain.card.CardEffect
import domain.deck.Deck
import domain.deck.DiscardPile
import domain.game.Game
import domain.player.Player
import domain.tower.Tower
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import repository.GameRepository
import service.GameService
import validation.MoveValidator

class GameIntegrationTest {

    @Test
    fun full_game_flow_should_work() {
        val p1 = Player("P1", Tower())
        val p2 = Player("P2", Tower())

        p1.tower.addCard(Card(3, CardEffect.MOVE_UP_2))
        p1.tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        p1.tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        p2.tower.addCard(Card(5, CardEffect.MOVE_UP_2))
        p2.tower.addCard(Card(4, CardEffect.MOVE_UP_2))

        val game = Game(
            players = listOf(p1, p2),
            deck = Deck(mutableListOf(Card(99, CardEffect.MOVE_UP_2))),
            discardPile = DiscardPile()
        )
        val service = GameService(game, GameRepository(), MoveValidator())
        service.performAction(SwapNeighborsAction(0))
        assertFalse(game.isFinished)
        assertEquals(p1.name, game.currentPlayer.name)
        service.endTurn()
        assertEquals(p2.name, game.currentPlayer.name)
    }
}