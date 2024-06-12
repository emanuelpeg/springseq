package com.assembly.springseq.model

import kotlin.random.Random

class Game {

    val id : java.util.UUID = java.util.UUID.randomUUID()
    private var sequence = this.getSequence()
    private var points: Int = 0

    private fun getSequence() : Sequence {
        val i = Random.nextInt(0,4)
        return when (i) {
            0 -> SequenceEven()
            1 -> SequencePow()
            2 -> SequenceFibbonacci()
            else -> SequenceOdd()
        }
    }

    fun getPoints() = this.points

    fun getOne() = sequence.getNumbers()[0]

    fun getTwo() = sequence.getNumbers()[1]

    fun getFour() = sequence.getNumbers()[3]

    fun isOk(nro : Int) : Boolean {
        if (sequence.getNumbers()[2] == nro) {
            this.points++
            this.sequence = this.getSequence()
            return true
        } else {
            this.points--
            this.sequence = this.getSequence()
            return false
        }
    }
}