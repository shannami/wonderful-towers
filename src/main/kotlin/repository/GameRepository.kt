package repository

import domain.game.Game
import java.sql.*

class GameRepository(private val connectionString: String = "jdbc:sqlite:game_history.db") : IGameRepository {
    private val connection: Connection = DriverManager.getConnection(connectionString)

    init {
        createTables()
    }

    private fun createTables() {
        val stmt = connection.createStatement()
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                start_time TEXT NOT NULL,
                end_time TEXT NOT NULL,
                winner_name TEXT,
                total_moves INTEGER,
                player1_name TEXT NOT NULL,
                player2_name TEXT NOT NULL
            )
        """)
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS moves (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                game_id INTEGER NOT NULL,
                move_number INTEGER NOT NULL,
                player_name TEXT NOT NULL,
                action_name TEXT NOT NULL,
                FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
            )
        """)
        stmt.close()
    }

    override fun save(game: Game) {
        if (!game.isFinished) return
        val now = System.currentTimeMillis()
        val winner = game.winner?.name
        val player1Name = game.players[0].name
        val player2Name = game.players[1].name

        val insertGame = connection.prepareStatement(
            "INSERT INTO games (start_time, end_time, winner_name, total_moves, player1_name, player2_name) VALUES (?, ?, ?, ?, ?, ?)"
        )
        insertGame.setString(1, now.toString())
        insertGame.setString(2, now.toString())
        insertGame.setString(3, winner)
        insertGame.setInt(4, game.moveHistory.size)
        insertGame.setString(5, player1Name)
        insertGame.setString(6, player2Name)
        insertGame.executeUpdate()
        insertGame.close()

        val stmt = connection.createStatement()
        val rs = stmt.executeQuery("SELECT last_insert_rowid()")
        rs.next()
        val gameId = rs.getInt(1)
        rs.close()
        stmt.close()

        val insertMove = connection.prepareStatement(
            "INSERT INTO moves (game_id, move_number, player_name, action_name) VALUES (?, ?, ?, ?)"
        )
        game.moveHistory.forEachIndexed { idx, move ->
            insertMove.setInt(1, gameId)
            insertMove.setInt(2, idx + 1)
            insertMove.setString(3, move.playerName)
            insertMove.setString(4, move.actionName)
            insertMove.executeUpdate()
        }
        insertMove.close()
    }

    override fun load(): Game? = null

    override fun getPlayerStats(): List<PlayerStat> {
        val stmt = connection.createStatement()
        val rs = stmt.executeQuery("""
            SELECT 
                all_players.player_name,
                COUNT(DISTINCT g.id) as games_played,
                SUM(CASE WHEN g.winner_name = all_players.player_name THEN 1 ELSE 0 END) as games_won,
                COALESCE(SUM(m.move_count), 0) as total_moves
            FROM (
                SELECT player1_name as player_name FROM games
                UNION
                SELECT player2_name FROM games
            ) all_players
            LEFT JOIN games g ON all_players.player_name = g.player1_name OR all_players.player_name = g.player2_name
            LEFT JOIN (
                SELECT game_id, COUNT(*) as move_count FROM moves GROUP BY game_id
            ) m ON g.id = m.game_id
            GROUP BY all_players.player_name
        """)
        val stats = mutableListOf<PlayerStat>()
        while (rs.next()) {
            stats.add(PlayerStat(
                name = rs.getString("player_name"),
                gamesPlayed = rs.getInt("games_played"),
                gamesWon = rs.getInt("games_won"),
                totalMovesMade = rs.getInt("total_moves")
            ))
        }
        rs.close()
        stmt.close()
        return stats
    }

    override fun getAllGames(): List<GameRecord> {
        val stmt = connection.createStatement()
        val rs = stmt.executeQuery("SELECT id, start_time, end_time, winner_name, total_moves FROM games ORDER BY id DESC")
        val games = mutableListOf<GameRecord>()
        while (rs.next()) {
            games.add(GameRecord(
                id = rs.getInt("id"),
                startTime = rs.getString("start_time").toLong(),
                endTime = rs.getString("end_time").toLong(),
                winnerName = rs.getString("winner_name"),
                totalMoves = rs.getInt("total_moves")
            ))
        }
        rs.close()
        stmt.close()
        return games
    }
}

data class PlayerStat(
    val name: String,
    val gamesPlayed: Int,
    val gamesWon: Int,
    val totalMovesMade: Int
)

data class GameRecord(
    val id: Int,
    val startTime: Long,
    val endTime: Long,
    val winnerName: String?,
    val totalMoves: Int
)