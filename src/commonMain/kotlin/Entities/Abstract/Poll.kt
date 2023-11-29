package Entities.Abstract

import Entities.Interfaces.Competition
import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.Ranking
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

abstract class Poll <S : ScoreMetrics, V : Vote>{

    abstract val pollAlgorithm : PollAlgorithm<V>
    abstract val competition : Competition<S>
    abstract val votes : List<V>

    fun computePoll() : Ranking {
        return pollAlgorithm.computeByAlgorithmRules(votes)
    }
}