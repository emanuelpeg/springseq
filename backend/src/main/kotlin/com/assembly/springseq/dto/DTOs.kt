package com.assembly.springseq.dto

import java.util.UUID

data class SequenceDTO(val first : Int, val second : Int, val four : Int, var gameId: UUID)

data class ResultDTO(val win : Boolean, val points : Int)