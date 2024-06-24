package com.assembly.springseq.model

import kotlin.random.Random

class SequenceLucca : Sequence() {

    override fun generateNumbers(): Array<Int> {
        val start = Random.nextInt(1, 13)
        return Array(4) { i -> getLucca(start + i) }
    }

    private fun getLucca(i: Int): Int {
        var lucca = 2
        var antLucca = 1
        var aux: Int
        var index = 0
        while (index < i) {
            aux = lucca
            lucca = aux + antLucca
            antLucca = aux
            index++
        }
        return lucca
    }
}