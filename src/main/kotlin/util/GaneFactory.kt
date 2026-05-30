package util

import domain.card.Card
import domain.card.CardEffect
import domain.deck.Deck
import domain.deck.DiscardPile
import domain.game.Game
import domain.player.Player
import domain.tower.Tower
import kotlin.random.Random

object GameFactory {

    fun createGame(): Game {
        val deckCards = (1..84).map { id ->
            Card(id = id, effect = randomEffect())
        }.shuffled().toMutableList()

        val players = listOf(
            Player("Player 1", Tower()),
            Player("Player 2", Tower())
        )

        players.forEach { player ->
            repeat(7) {
                val card = deckCards.removeFirst()
                player.tower.addCard(card)
            }
            player.tower.sortByDescendingId()
        }

        val deck = Deck(deckCards)
        val game = Game(
            players = players,
            deck = deck,
            discardPile = DiscardPile()
        )

        deck.draw()?.let { card ->
            game.commonCards.add(card)
        }

        return game
    }

    private fun randomEffect(): CardEffect {
        val effects = CardEffect.entries
        return effects[Random.nextInt(effects.size)]
    }
}