package com.assembly.springseq.repo

import com.assembly.springseq.model.Game
import java.util.UUID

interface GameDao {

    fun get(gameId: UUID): Game?

    fun getOrDefault(gameId: UUID): Game

    fun exists(gameId: UUID): Boolean

    fun save(game: Game)

    fun removeOldGames()
}