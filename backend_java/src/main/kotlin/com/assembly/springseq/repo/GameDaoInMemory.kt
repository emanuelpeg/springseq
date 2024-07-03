package com.assembly.springseq.repo

import com.assembly.springseq.model.Game
import com.assembly.springseq.model.GameFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

@Repository
@Profile("inMemory")
class GameDaoInMemory : GameDao {

    private var repo = HashMap<UUID, Game>()

    override fun get(gameId: UUID): Game? {
        return repo[gameId]
    }

    override fun getOrDefault(gameId: UUID): Game {
        return repo.getOrDefault(gameId, GameFactory.create())
    }

    override fun exists(gameId: UUID): Boolean {
        return repo.contains(gameId)
    }

    override fun save(game: Game) {
        repo[game.id] = game
    }

    override fun removeOldGames() {
        val games = repo.filter { it.value.getCreationDate() < LocalDateTime.now().plusDays(-1) }
        games.forEach { repo.remove(it.key) }
    }
}