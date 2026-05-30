// Tower.kt
package domain.tower

import domain.card.Card

class Tower {
    private val cards = mutableListOf<Card>()

    // Методы для чтения состояния
    fun size(): Int = cards.size
    fun isEmpty(): Boolean = cards.isEmpty()
    fun lastIndex(): Int = cards.lastIndex
    fun isValidIndex(index: Int): Boolean = index in cards.indices
    fun getCard(index: Int): Card? = cards.getOrNull(index)
    fun toList(): List<Card> = cards.toList()

    // Методы для модификации
    fun addCard(card: Card) {
        cards.add(card)
    }

    fun sortByDescendingId() {
        cards.sortByDescending { it.id }
    }

    fun swap(i: Int, j: Int) {
        if (!isValidIndex(i) || !isValidIndex(j)) return
        val tmp = cards[i]
        cards[i] = cards[j]
        cards[j] = tmp
    }

    fun removeAt(index: Int): Card? {
        if (!isValidIndex(index)) return null
        return cards.removeAt(index)
    }

    fun insertAt(index: Int, card: Card) {
        val safeIndex = index.coerceIn(0, cards.size)
        cards.add(safeIndex, card)
    }

    fun isSorted(): Boolean {
        if (cards.size < 2) return true
        for (i in 0 until cards.size - 1) {
            if (cards[i].id > cards[i + 1].id) return false
        }
        return true
    }
}