package com.assembly.springseq.model

import kotlin.random.Random

class SequenceEven : Sequence() {

    override fun generateNumbers(): Array<Int> {
        val start = Random.nextInt(-10, 200) * 2
        return Array(4) { i -> (start + (i * 2)) }
    }

}