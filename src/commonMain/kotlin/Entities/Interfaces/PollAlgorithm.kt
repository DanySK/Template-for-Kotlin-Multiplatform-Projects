package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface PollAlgorithm<S: ScoreMetrics, T : Vote>{
    val pollAlgorithmParameters : List<PollAlgorithmParameter>

    fun computeByAlgorithmRules(votes : List<T> ) : Ranking<S>
}