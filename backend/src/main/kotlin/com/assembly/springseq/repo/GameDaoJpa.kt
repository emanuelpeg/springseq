package com.assembly.springseq.repo

import com.assembly.springseq.model.Game
import com.assembly.springseq.model.GameFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GameRepository : JpaRepository<Game, UUID> {

}

@Repository
@Profile("jpa")
class GameDaoJpa : GameDao {

    @Autowired
    lateinit var repository : GameRepository

    override fun get(gameId: UUID): Game? {
        return repository.findByIdOrNull(gameId)
    }

    override fun getOrDefault(gameId: UUID): Game {
        val game = repository.findByIdOrNull(gameId)
        return (game ?: GameFactory.create())
    }

    override fun exists(gameId: UUID): Boolean {
        return repository.existsById(gameId)
    }

    override fun save(game: Game) {
        repository.save(game)
    }
}