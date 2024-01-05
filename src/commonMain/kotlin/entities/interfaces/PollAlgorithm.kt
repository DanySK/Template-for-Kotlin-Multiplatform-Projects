package entities.interfaces

import entities.types.ScoreMetrics

/**
 * This interface represents the algorithm chosen to compute the final ranking.
 */
interface PollAlgorithm<S : ScoreMetrics, V : Vote> {
    /**
     * List of parameters.
     */
    var pollAlgorithmParameters: List<PollAlgorithmParameter>

    /**
     * Compute the final ranking, given the votes.
     */
    fun computeByAlgorithmRules(votes: List<V>): Ranking<S>

    /**
     * Shortcut useful to add an element in [pollAlgorithmParameters].
     */
    operator fun PollAlgorithmParameter.unaryPlus() {
        pollAlgorithmParameters += this@unaryPlus
    }
}
