package entities.interfaces

import entities.types.ScoreMetrics

/**
 * This interface represents a competitor.
 */
interface Competitor<S : ScoreMetrics> {
    /**
     * Name of competitor.
     */
    var name: String

    /**
     * List of scores realized.
     */
    var scores: List<Score<S>>

    /**
     * Shortcut useful to assign the [name].
     */
    operator fun String.unaryMinus()

    /**
     * Shortcut useful to add an element in [scores].
     */
    operator fun Score<S>.unaryPlus()
}
