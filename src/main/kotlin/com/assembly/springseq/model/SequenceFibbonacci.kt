package com.assembly.springseq.model

import kotlin.random.Random

class SequenceFibbonacci : Sequence() {

    override fun generateNumbers(): Array<Int> {
        val start = Random.nextInt(1, 13)
        return Array(4) { i -> getFibbonnacci(start + i) }
    }

    private fun getFibbonnacci(i: Int): Int {
        var fibo = 1
        var antFibo = 0
        var aux: Int
        var index = 0
        while (index < i) {
            aux = fibo
            fibo = aux + antFibo
            antFibo = aux
            index++
        }
        return fibo
    }
}