package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.model.GameFactory
import com.assembly.springseq.model.exception.NotFoundException
import com.assembly.springseq.repo.GameDao
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GameServiceImpl : GameService {

    val log = LoggerFactory.getLogger(GameServiceImpl::class.java)

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

    override fun getPoints(gameId: UUID): Int {
        if (gameDao.exists(gameId)) {
            val game = gameDao.get(gameId)!!
            return game.getPoints()
        }
        return 0
    }

    @Scheduled(fixedRate = 100_000)
    fun removeOldGames() {
        log.info("--- Removing old games ---")
        gameDao.removeOldGames()
    }
}