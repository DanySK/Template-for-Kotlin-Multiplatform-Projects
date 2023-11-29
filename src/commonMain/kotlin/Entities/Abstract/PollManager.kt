package Entities.Abstract

import Entities.Interfaces.Ranking
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

abstract class PollManager<S : ScoreMetrics, V : Vote> {
    abstract val polls: List<Poll<S, V>>

    fun computeAllPolls() : List<Ranking> {
        val rankings = mutableListOf<Ranking>()
        polls.forEach {  rankings += it.computePoll() }
        return rankings
    }
}