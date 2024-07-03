package com.assembly.springseq.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/HealthCheck")
class HealthCheck {

    @GetMapping("/check")
    fun check(): ResponseEntity<Boolean> = ResponseEntity.ok(true)

}