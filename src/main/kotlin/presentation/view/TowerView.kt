package presentation.view
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import domain.card.Card
@Composable
fun TowerView(
    cards: List<Card>,
    onCardClick: (Int) -> Unit
) {
    Column {
        cards.forEachIndexed { index, card ->
            CardView(
                card = card,
                onClick = {
                    onCardClick(index)
                },
                backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFF3CD)
            )
        }
    }
}