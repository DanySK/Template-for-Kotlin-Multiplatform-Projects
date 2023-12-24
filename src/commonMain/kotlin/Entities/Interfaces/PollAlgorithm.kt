package Entities.Interfaces

import Entities.Abstract.Ranking
import Entities.Types.ScoreMetrics

interface PollAlgorithm<S: ScoreMetrics, V : Vote>  {
    var pollAlgorithmParameters : List<PollAlgorithmParameter>
    fun computeByAlgorithmRules(votes : List<V>) : Ranking<S>
}