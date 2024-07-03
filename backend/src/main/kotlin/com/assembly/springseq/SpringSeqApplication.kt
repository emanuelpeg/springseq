package com.assembly.springseq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpringSeqApplication

fun main(args: Array<String>) {
	runApplication<SpringSeqApplication>(*args)
}
