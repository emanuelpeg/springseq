package com.assembly.springseq.model

import kotlin.random.Random

class SequenceMultiple : Sequence() {

    override fun generateNumbers(): Array<Int> {
        val start = Random.nextInt(-10, 20)
        val multiple = Random.nextInt(3, 9)
        return Array(4) { i -> (start + i) * multiple}
    }

}