package entities.interfaces

import entities.types.ScoreMetrics
/**
 * This interface represents a manager that builds then executes one or more poll.
 */
interface PollManager<S : ScoreMetrics, V : Vote> {

    /**
     * List of polls to be managed.
     */
    var pollList: List<Poll<S, V>>

    /**
     * Computes every [Poll] defined, return a list of [Ranking].
     */
    fun computeAllPolls(): List<Ranking<S>>

    /**
     * DSL-function useful to initialize the manager.
     */
    infix fun initializedAs(initializer: PollManager<S, V>.() -> Unit): PollManager<S, V>

    /**
     * Shortcut to add a poll in [pollList].
     */
    operator fun Poll<S, V>.unaryPlus()

    /**
     * DSL-function useful to initialize a poll.
     */
    fun poll(newPoll: Poll<S, V>.() -> Unit): Poll<S, V>
}
