package Entities.Abstract

import Entities.Types.ScoreMetrics

abstract class Score<T : ScoreMetrics>{
    abstract var scoreValue : T

    operator fun T.unaryMinus(){
        scoreValue = this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Score<*>) return false

        if (scoreValue != other.scoreValue) return false

        return true
    }

    override fun hashCode(): Int {
        return scoreValue.hashCode()
    }

    override fun toString(): String {
        return "Score(scoreValue=$scoreValue)"
    }


}