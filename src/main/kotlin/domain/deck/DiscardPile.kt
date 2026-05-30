package domain.deck

import domain.card.Card

class DiscardPile {
    val cards = mutableListOf<Card>()
    fun add(card: Card) {
        cards.add(card)
    }

}