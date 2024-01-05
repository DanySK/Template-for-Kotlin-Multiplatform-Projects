package entities.abstract

import entities.interfaces.Score
import entities.types.ScoreMetrics

/**
 *
 */
abstract class ScoreAbstraction<T : ScoreMetrics> : Score<T> {
    override lateinit var scoreValue: T

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScoreAbstraction<*>) return false

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
