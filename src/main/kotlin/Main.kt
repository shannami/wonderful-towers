import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.screen.GameScreen
import presentation.viewmodel.GameViewModel
import repository.GameRepository
import service.GameService
import util.GameFactory
import validation.MoveValidator

fun main() = application {
    val game = GameFactory.createGame()
    val repository = GameRepository()
    val validator = MoveValidator()
    val service = GameService(game, repository, validator)
    val viewModel = GameViewModel(service)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Wonderful Tower"
    ) {
        GameScreen(viewModel, repository)
    }
}