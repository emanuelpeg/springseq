package com.assembly.springseq.model

abstract class Sequence {

    private val numbers = this.generateNumbers()

    abstract fun generateNumbers() : Array<Int>

    fun getNumbers() = numbers

    override fun hashCode(): Int {

        return numbers.contentHashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sequence) return false

        if (!numbers.contentEquals(other.numbers)) return false

        return true
    }

}