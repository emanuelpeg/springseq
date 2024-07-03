package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.model.exception.NotFoundException
import java.util.UUID

interface GameService {

    fun startGame() : UUID

    fun getSequence(gameId : UUID) : SequenceDTO

    @Throws(NotFoundException::class)
    fun isOk(gameId : UUID, value: Int) : ResultDTO

    fun getPoints(gameId : UUID) : Int
}