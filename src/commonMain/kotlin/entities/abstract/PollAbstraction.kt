package entities.abstract

import entities.interfaces.Competition
import entities.interfaces.Poll
import entities.interfaces.PollAlgorithm
import entities.interfaces.Ranking
import entities.interfaces.Vote
import entities.types.ScoreMetrics

/**
 *
 */
abstract class PollAbstraction<S : ScoreMetrics, V : Vote> : Poll<S, V> {
    override lateinit var pollAlgorithm: PollAlgorithm<S, V>
    override lateinit var competition: Competition<S>
    override lateinit var votesList: List<V>

    override fun computePoll(): Ranking<S> = pollAlgorithm.computeByAlgorithmRules(votesList)

    /**
     * Shortcut which allows competitors' name chaining in the list.
     */
    infix fun List<String>.then(s: String) = this + s

    /**
     * Shortcut which allows competitors' name chaining in the list.
     */
    infix fun String.then(s: String) = listOf(this, s)
}
