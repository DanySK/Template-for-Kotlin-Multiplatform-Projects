package Entities.Abstract

import Entities.Implementations.PollSimulation
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

abstract class PollManager<S : ScoreMetrics, V : Vote> {
     private lateinit var pollList: List<Poll<S, V>>

    fun computeAllPolls() : List<Ranking<S>> {
        val rankings = mutableListOf<Ranking<S>>()
        pollList.forEach {  rankings.add(it.computePoll()) }
        return rankings.toList()
    }


    infix fun initializedAs(initializer: PollManager<S,V>.() -> Unit): PollManager<S,V>{
        return object : PollManager<S,V>(){}.
        apply(initializer)
    }
    operator fun PollSimulation<S, V>.unaryPlus(){
        if(!this@PollManager::pollList.isInitialized)
            this@PollManager.pollList = listOf()
        this@PollManager.pollList += this@unaryPlus
    }
    fun poll(newPoll : PollSimulation<S, V>.() -> Unit): PollSimulation<S, V> {
        return PollSimulation<S,V>().apply(newPoll)
    }
}