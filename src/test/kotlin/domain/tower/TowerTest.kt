package domain.tower

import domain.card.Card
import domain.card.CardEffect
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TowerTest {

    @Test
    fun swap_should_exchange_cards() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        tower.swap(0, 1)
        assertEquals(2, tower.toList()[0].id)
        assertEquals(1, tower.toList()[1].id)
    }

    @Test
    fun removeAt_should_remove_and_return_card() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        val removed = tower.removeAt(0)
        assertEquals(1, removed?.id)
        assertEquals(1, tower.size())
        assertEquals(2, tower.toList()[0].id)
    }

    @Test
    fun insertAt_should_add_card_at_valid_index() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(3, CardEffect.MOVE_UP_2))
        tower.insertAt(1, Card(2, CardEffect.MOVE_UP_2))
        assertEquals(2, tower.toList()[1].id)
        assertEquals(3, tower.size())
    }

    @Test
    fun isSorted_should_return_true_for_sorted() {
        val tower = Tower()
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        tower.addCard(Card(2, CardEffect.MOVE_UP_2))
        tower.addCard(Card(3, CardEffect.MOVE_UP_2))
        assertTrue(tower.isSorted())
    }

    @Test
    fun isSorted_should_return_false_for_unsorted() {
        val tower = Tower()
        tower.addCard(Card(3, CardEffect.MOVE_UP_2))
        tower.addCard(Card(1, CardEffect.MOVE_UP_2))
        assertFalse(tower.isSorted())
    }
}