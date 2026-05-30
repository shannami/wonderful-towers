package domain.deck

import domain.card.Card

class Deck(
    val cards: MutableList<Card>
) {
    fun draw(): Card? {
        if (cards.isEmpty()) {
            return null
        }
        return cards.removeFirst()
    }

    fun isEmpty(): Boolean {
        return cards.isEmpty()
    }

}