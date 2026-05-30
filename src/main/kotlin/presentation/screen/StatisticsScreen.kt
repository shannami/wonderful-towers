package presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import repository.IGameRepository

@Composable
fun StatisticsScreen(
    repository: IGameRepository,
    onBack: () -> Unit
) {
    val stats = remember { mutableStateOf(repository.getPlayerStats()) }
    val games = remember { mutableStateOf(repository.getAllGames()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) { Text("← Назад") }
        Text("Статистика игроков")
        stats.value.forEach { stat ->
            Text("${stat.name}: игры ${stat.gamesPlayed}, победы ${stat.gamesWon}, ходов ${stat.totalMovesMade}")
        }
        Text("История партий")
        games.value.forEach { game ->
            Text("Партия #${game.id} | Победитель: ${game.winnerName ?: "нет"} | Ходов: ${game.totalMoves}")
        }
    }
}