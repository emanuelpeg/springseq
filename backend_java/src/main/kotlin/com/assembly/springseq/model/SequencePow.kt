package com.assembly.springseq.model

import kotlin.math.pow
import kotlin.random.Random

class SequencePow : Sequence() {

    override fun generateNumbers(): Array<Int> {
        val value = Random.nextInt(2, 10)
        return Array(4) { i -> value.toDouble().pow(i.toDouble()).toInt() }
    }

}