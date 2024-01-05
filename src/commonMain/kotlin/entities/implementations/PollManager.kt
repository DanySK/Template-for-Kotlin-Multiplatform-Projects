package entities.implementations

import entities.interfaces.Poll
import entities.interfaces.Ranking
import entities.interfaces.Vote
import entities.types.ScoreMetrics

/**
 * This class allows to define and execute multiple polls.
 */
class PollManager<S : ScoreMetrics, V : Vote> {
    private lateinit var pollList: List<Poll<S, V>>

    /**
     * Computes every [Poll] defined, return a list of [Ranking].
     */
    fun computeAllPolls(): List<Ranking<S>> {
        val rankings = mutableListOf<Ranking<S>>()
        pollList.forEach { rankings.add(it.computePoll()) }
        return rankings.toList()
    }

    /**
     * DSL-function useful to initialize the manager.
     */
    infix fun initializedAs(initializer: PollManager<S, V>.() -> Unit): PollManager<S, V> {
        return PollManager<S, V>()
            .apply(initializer)
    }

    /**
     * Shortcut to add a poll in [pollList].
     */
    operator fun PollSimulation<S, V>.unaryPlus() {
        if (!this@PollManager::pollList.isInitialized) {
            this@PollManager.pollList = listOf()
        }
        this@PollManager.pollList += this@unaryPlus
    }

    /**
     * DSL-function useful to initialize a poll.
     */
    fun poll(newPoll: PollSimulation<S, V>.() -> Unit): PollSimulation<S, V> {
        return PollSimulation<S, V>().apply(newPoll)
    }
}
