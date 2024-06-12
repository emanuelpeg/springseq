package com.assembly.springseq.service

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import java.util.UUID

interface GameService {

    fun startGame() : UUID

    fun getSequence(gameId : UUID) : SequenceDTO

    fun isOk(gameId : UUID, value: Int) : ResultDTO

}