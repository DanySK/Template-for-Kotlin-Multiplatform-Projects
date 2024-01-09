package entities.abstract

import entities.interfaces.Competitor
import entities.interfaces.Score
import entities.types.ScoreMetrics

/**
 *
 */
abstract class CompetitorAbstraction<S : ScoreMetrics> : Competitor<S> {
    override lateinit var name: String
    override var scores: List<Score<S>> = listOf()

    override operator fun String.unaryMinus() {
        this@CompetitorAbstraction.name = this@unaryMinus
    }

    override operator fun Score<S>.unaryPlus() {
        this@CompetitorAbstraction.scores += this@unaryPlus
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompetitorAbstraction<*>) return false

        if (name != other.name) return false
        if (scores != other.scores) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + scores.hashCode()
        return result
    }

    override fun toString(): String {
        return "Competitor(name='$name', scores=$scores)"
    }
}
