package presentation.view
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import domain.card.Card
@Composable
fun CardView(
    card: Card,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = Color(0xFFFFF3CD)
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        onClick = { onClick?.invoke() }
    ) {
        Text("${card.id}\n${card.effect}")
    }
}