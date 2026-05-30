package service

import domain.action.DestroyTopAction
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
import validation.MoveValidator

class GameServiceTest {

    private fun createGame(players: List<Player>, deckCards: MutableList<Card> = mutableListOf()): Game {
        return Game(players, Deck(deckCards), DiscardPile())
    }

    @Test
    fun turn_should_change_after_action() {
        val p1 = Player("P1", Tower())
        val p2 = Player("P2", Tower())
        p1.tower.addCard(Card(1, CardEffect.DESTROY_TOP))
        val game = createGame(listOf(p1, p2))
        val gameService = GameService(game, GameRepository(), MoveValidator())

        gameService.performAction(DestroyTopAction())
        assertEquals(p1.name, game.currentPlayer.name)
        gameService.endTurn()
        assertEquals(p2.name, game.currentPlayer.name)
    }

    @Test
    fun use_common_card_should_remove_and_discard() {
        val game = createGame(listOf(Player("P1", Tower())))
        val service = GameService(game, GameRepository(), MoveValidator())
        val card = Card(10, CardEffect.MOVE_UP_2)
        game.commonCards.add(card)
        service.useCommonCard(card)
        assertTrue(game.commonCards.isEmpty())
        assertTrue(game.discardPile.cards.contains(card))
    }

    @Test
    fun game_should_finish_when_tower_becomes_sorted_after_action() {
        val tower = Tower()
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        val player = Player("P1", tower)
        val game = createGame(listOf(player))
        val service = GameService(game, GameRepository(), MoveValidator())
        service.performAction(SwapNeighborsAction(0))
        assertTrue(game.isFinished)
        assertEquals(player, game.winner)
    }
}