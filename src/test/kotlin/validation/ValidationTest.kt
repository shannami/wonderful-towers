package validation

import domain.action.*
import domain.card.Card
import domain.card.CardEffect
import domain.deck.Deck
import domain.deck.DiscardPile
import domain.game.Game
import domain.player.Player
import domain.tower.Tower
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoveValidatorTest {

    private lateinit var game: Game
    private lateinit var validator: MoveValidator

    @BeforeEach
    fun setUp() {
        val tower = Tower()
        (1..5).forEach { tower.addCard(Card(it, CardEffect.MOVE_UP_2)) }
        val player = Player("P1", tower)
        game = Game(listOf(player), Deck(mutableListOf()), DiscardPile())
        validator = MoveValidator()
    }

    @Test
    fun validateReplaceBlockAction_validIndex() {
        assertTrue(validator.validate(game, ReplaceBlockAction(2)))
        assertFalse(validator.validate(game, ReplaceBlockAction(10)))
    }

    @Test
    fun validateMoveUp2Action() {
        assertTrue(validator.validate(game, MoveUp2Action(2)))
        assertFalse(validator.validate(game, MoveUp2Action(1)))
        assertFalse(validator.validate(game, MoveUp2Action(10)))
    }

    @Test
    fun validateMoveDown2Action() {
        assertTrue(validator.validate(game, MoveDown2Action(2)))
        assertFalse(validator.validate(game, MoveDown2Action(3)))
        assertFalse(validator.validate(game, MoveDown2Action(10)))
    }

    @Test
    fun validateSwapNeighborsAction() {
        assertTrue(validator.validate(game, SwapNeighborsAction(3)))
        assertFalse(validator.validate(game, SwapNeighborsAction(4)))
        assertFalse(validator.validate(game, SwapNeighborsAction(-1)))
    }

    @Test
    fun validateSwapWithGapAction() {
        assertTrue(validator.validate(game, SwapWithGapAction(2)))
        assertFalse(validator.validate(game, SwapWithGapAction(3)))
        assertFalse(validator.validate(game, SwapWithGapAction(10)))
    }

    @Test
    fun validateDestroyActions() {
        assertTrue(validator.validate(game, DestroyTopAction()))
        assertTrue(validator.validate(game, DestroyMiddleAction()))
        assertTrue(validator.validate(game, DestroyBottomAction()))
        val emptyTower = Tower()
        val emptyGame = Game(listOf(Player("P2", emptyTower)), Deck(mutableListOf()), DiscardPile())
        assertFalse(validator.validate(emptyGame, DestroyTopAction()))
        assertFalse(validator.validate(emptyGame, DestroyMiddleAction()))
        assertFalse(validator.validate(emptyGame, DestroyBottomAction()))
    }
}