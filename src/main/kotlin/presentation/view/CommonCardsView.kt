package presentation.view

import androidx.compose.foundation.layout.Row

import androidx.compose.runtime.Composable

import domain.card.Card

@Composable

fun CommonCardsView(
    cards: List<Card>,
    onCardClick: (Card) -> Unit
) {
    Row {
        cards.forEach { card ->
            androidx.compose.material.Button(
                onClick = {
                    onCardClick(card)
                }
            ) {
                androidx.compose.material.Text(
                    text = "${card.id}\n${card.effect}"
                )
            }
        }
    }
}