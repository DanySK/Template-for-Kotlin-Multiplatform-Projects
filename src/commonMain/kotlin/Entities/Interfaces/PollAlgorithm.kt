package Entities.Interfaces

import Entities.Types.ScoreMetrics

typealias NumberOfVotes = Int
interface PollAlgorithm<T : Vote>{
    val pollAlgorithmParameters : List<PollAlgorithmParameter>

    fun computeByAlgorithmRules(votes : List<T> ) : Ranking
}