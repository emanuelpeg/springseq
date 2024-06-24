package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.model.Game
import com.assembly.springseq.model.GameFactory
import com.assembly.springseq.model.exception.NotFoundException
import com.assembly.springseq.repo.GameDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GameServiceImpl : GameService {

    @Autowired
    lateinit var gameDao : GameDao

    override fun startGame(): UUID {
        val game = GameFactory.create()
        gameDao.save(game)
        return game.id
    }

    override fun getSequence(gameId: UUID): SequenceDTO {
        val game = gameDao.getOrDefault(gameId)
        val sequence = SequenceDTO(game.getOne(), game.getTwo(), game.getFour(), game.id)
        gameDao.save(game)
        return sequence
    }

    @Throws(NotFoundException::class)
    override fun isOk(gameId: UUID, value: Int): ResultDTO {
        if (gameDao.exists(gameId)) {
            val game = gameDao.get(gameId)!!
            val result = ResultDTO(game.isOk(value), game.getPoints())
            gameDao.save(game)
            return result
        }
        throw NotFoundException("Game Id $gameId is not exist")
    }
}