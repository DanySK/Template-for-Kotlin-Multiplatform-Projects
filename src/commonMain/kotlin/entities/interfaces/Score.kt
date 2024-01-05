package entities.interfaces

import entities.types.ScoreMetrics

/**
 * This interface represents a score, valued depending on its type.
 */
interface Score<T : ScoreMetrics> {
    /**
     * Score value.
     */
    var scoreValue: T
}
