package com.assembly.springseq.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import kotlin.random.Random

@Entity
class Game {

    @Id
    val id : java.util.UUID = java.util.UUID.randomUUID()

    @Column
    private var numbers = Array(4) { 0 }

    @Column(name = "points")
    private var points = 0

    fun setNumbers() {
        val i = Random.nextInt(0,7)
        val sequence = when (i) {
            0 -> SequenceEven()
            1 -> SequencePow()
            2 -> SequenceFibbonacci()
            3 -> SequenceLine()
            4 -> SequenceLucca()
            5 -> SequenceMultiple()
            else -> SequenceOdd()
        }
        this.numbers = sequence.generateNumbers()
    }

    fun getPoints() = this.points

    fun getOne() = numbers[0]

    fun getTwo() = numbers[1]

    private fun getTree() = numbers[2]

    fun getFour() = numbers[3]

    fun isOk(nro : Int) : Boolean {
        if (getTree() == nro) {
            this.points++
            this.setNumbers()
            return true
        } else {
            this.points--
            this.setNumbers()
            return false
        }
    }
}

object GameFactory {
    fun create() : Game {
        val game = Game()
        game.setNumbers()
        return game
    }
}