package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface Poll <S : ScoreMetrics, V : Vote>{

    val pollAlgorithm : PollAlgorithm<V>
    val competition : Competition<S>
    val votes : List<V>

    fun computePoll() : Ranking {
        return pollAlgorithm.computeByAlgorithmRules(votes)
    }
}