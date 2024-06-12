package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.model.Game
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.HashMap

@Service
class GameServiceImpl : GameService {

    private var repo = HashMap<UUID, Game>()

    override fun startGame(): UUID {
        val game = Game()
        repo[game.id] = game
        return game.id
    }

    override fun getSequence(gameId: UUID): SequenceDTO {
        val game = repo.getOrDefault(gameId, Game())
        val sequence = SequenceDTO(game.getOne(), game.getTwo(), game.getFour(), game.id)
        repo[game.id] = game
        return sequence
    }

    override fun isOk(gameId: UUID, value: Int): ResultDTO {
        if (repo.containsKey(gameId)) {
            val game = repo[gameId]!!
            val result = ResultDTO(game.isOk(value), game.getPoints())
            repo[gameId] = game
            return result
        }
        throw IllegalArgumentException("Game Id $gameId is not exist")
    }
}