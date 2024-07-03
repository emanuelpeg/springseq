package com.assembly.springseq.model

import kotlin.random.Random

class SequenceLine : Sequence() {

    override fun generateNumbers(): Array<Int> {
        val start = Random.nextInt(2, 100)
        return Array(4) { i -> start + i }
    }

}