package domain.action

import domain.card.Card
import domain.card.CardEffect
import domain.deck.Deck
import domain.deck.DiscardPile
import domain.game.Game
import domain.player.Player
import domain.tower.Tower
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ActionsTest {

    private fun createGameWithPlayer(vararg cardIds: Int): Game {
        val tower = Tower()
        cardIds.forEach { id ->
            tower.addCard(Card(id, CardEffect.MOVE_UP_2))
        }
        val player = Player("P1", tower)
        val deck = Deck(mutableListOf(Card(100, CardEffect.SWAP_NEIGHBORS)))
        return Game(listOf(player), deck, DiscardPile())
    }

    @Test
    fun moveUp2Action_shouldMoveCardTwoPositionsUp() {
        val game = createGameWithPlayer(10, 20, 30, 40)
        val action = MoveUp2Action(2)
        action.perform(game)
        val towerCards = game.currentPlayer.tower.toList()
        assertEquals(30, towerCards[0].id)
        assertEquals(10, towerCards[1].id)
        assertEquals(20, towerCards[2].id)
        assertEquals(40, towerCards[3].id)
    }

    @Test
    fun moveUp2Action_shouldDoNothingIfNotEnoughSpace() {
        val game = createGameWithPlayer(10, 20)
        val action = MoveUp2Action(1)
        action.perform(game)
        val towerCards = game.currentPlayer.tower.toList()
        assertEquals(10, towerCards[0].id)
        assertEquals(20, towerCards[1].id)
    }

    @Test
    fun moveDown2Action_shouldMoveCardTwoPositionsDown() {
        val game = createGameWithPlayer(10, 20, 30, 40)
        val action = MoveDown2Action(0)
        action.perform(game)
        val towerCards = game.currentPlayer.tower.toList()
        assertEquals(20, towerCards[0].id)
        assertEquals(30, towerCards[1].id)
        assertEquals(10, towerCards[2].id)
        assertEquals(40, towerCards[3].id)
    }

    @Test
    fun moveDown2Action_shouldDoNothingIfOutOfBounds() {
        val game = createGameWithPlayer(10, 20, 30)
        val action = MoveDown2Action(2)
        action.perform(game)
        val towerCards = game.currentPlayer.tower.toList()
        assertEquals(10, towerCards[0].id)
        assertEquals(20, towerCards[1].id)
        assertEquals(30, towerCards[2].id)
    }

    @Test
    fun swapNeighborsAction_shouldSwapAdjacent() {
        val game = createGameWithPlayer(1, 2, 3)
        val action = SwapNeighborsAction(1)
        action.perform(game)
        val towerCards = game.currentPlayer.tower.toList()
        assertEquals(1, towerCards[0].id)
        assertEquals(3, towerCards[1].id)
        assertEquals(2, towerCards[2].id)
    }

    @Test
    fun swapWithGapAction_shouldSwapWithTwoStepGap() {
        val game = createGameWithPlayer(5, 6, 7, 8)
        val action = SwapWithGapAction(0)
        action.perform(game)
        val towerCards = game.currentPlayer.tower.toList()
        assertEquals(7, towerCards[0].id)
        assertEquals(6, towerCards[1].id)
        assertEquals(5, towerCards[2].id)
        assertEquals(8, towerCards[3].id)
    }

    @Test
    fun destroyTopAction_shouldRemoveTopAndReplaceWithNewCard() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        val player = Player("P1", tower)
        val deck = Deck(mutableListOf(Card(99, CardEffect.DESTROY_TOP)))
        val game = Game(listOf(player), deck, DiscardPile())
        val action = DestroyTopAction()
        action.perform(game)
        assertEquals(1, game.commonCards.size)
        assertEquals(1, game.commonCards[0].id)
        val newTower = player.tower.toList()
        assertEquals(99, newTower[0].id)
        assertEquals(2, newTower[1].id)
    }

    @Test
    fun destroyMiddleAction_shouldRemoveMiddleAndReplace() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        tower.addCard(Card(3, CardEffect.MOVE_UP_2))
        val player = Player("P1", tower)
        val deck = Deck(mutableListOf(Card(99, CardEffect.DESTROY_MIDDLE)))
        val game = Game(listOf(player), deck, DiscardPile())
        val action = DestroyMiddleAction()
        action.perform(game)
        assertEquals(2, game.commonCards[0].id)
        val newTower = player.tower.toList()
        assertEquals(1, newTower[0].id)
        assertEquals(99, newTower[1].id)
        assertEquals(3, newTower[2].id)
    }

    @Test
    fun destroyBottomAction_shouldRemoveLastAndReplace() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        val player = Player("P1", tower)
        val deck = Deck(mutableListOf(Card(99, CardEffect.DESTROY_BOTTOM)))
        val game = Game(listOf(player), deck, DiscardPile())
        val action = DestroyBottomAction()
        action.perform(game)
        assertEquals(2, game.commonCards[0].id)
        val newTower = player.tower.toList()
        assertEquals(1, newTower[0].id)
        assertEquals(99, newTower[1].id)
    }

    @Test
    fun replaceBlockAction_shouldReplaceAndMoveOldToCommon() {
        val tower = Tower()
        tower.addCard(Card(5, CardEffect.MOVE_UP_2))
        tower.addCard(Card(6, CardEffect.MOVE_UP_2))
        val player = Player("P1", tower)
        val deck = Deck(mutableListOf(Card(77, CardEffect.SWAP_NEIGHBORS)))
        val game = Game(listOf(player), deck, DiscardPile())
        val action = ReplaceBlockAction(0)
        action.perform(game)
        assertEquals(5, game.commonCards[0].id)
        val newTower = player.tower.toList()
        assertEquals(77, newTower[0].id)
        assertEquals(6, newTower[1].id)
    }

    @Test
    fun replaceBlockAction_shouldDoNothingWhenDeckEmpty() {
        val tower = Tower()
        tower.addCard(Card(5, CardEffect.MOVE_UP_2))
        val player = Player("P1", tower)
        val deck = Deck(mutableListOf())
        val game = Game(listOf(player), deck, DiscardPile())
        val action = ReplaceBlockAction(0)
        action.perform(game)
        assertTrue(game.commonCards.isEmpty())
        assertEquals(5, player.tower.toList()[0].id)
    }
}