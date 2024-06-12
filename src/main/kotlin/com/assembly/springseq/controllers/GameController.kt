package com.assembly.springseq.controllers

import com.assembly.springseq.dto.ResultDTO
import com.assembly.springseq.dto.SequenceDTO
import com.assembly.springseq.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/")
class GameController {

    @Autowired
    lateinit var gameService : GameService

    @GetMapping("/startGame")
    fun startGame(): ResponseEntity<UUID> = ResponseEntity.ok(gameService.startGame())

    @GetMapping("/sequence")
    fun sequence(@RequestHeader gameId : String): ResponseEntity<SequenceDTO> =
        ResponseEntity.ok(gameService.getSequence(UUID.fromString(gameId)))

    @PostMapping("/isOk")
    fun isOk(@RequestHeader gameId : String, @RequestBody value : Int): ResponseEntity<ResultDTO> =
        ResponseEntity.ok(gameService.isOk(UUID.fromString(gameId), value))
}