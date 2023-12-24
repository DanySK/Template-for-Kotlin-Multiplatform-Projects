package Entities.Abstract

import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

abstract class PollManager<S : ScoreMetrics, V : Vote> {
     abstract var pollList: List<Poll<S, V>>

    fun computeAllPolls() : List<Ranking<S>> {
        val rankings = mutableListOf<Ranking<S>>()
        pollList.forEach {  rankings.add(it.computePoll()) }
        return rankings.toList()
    }
}