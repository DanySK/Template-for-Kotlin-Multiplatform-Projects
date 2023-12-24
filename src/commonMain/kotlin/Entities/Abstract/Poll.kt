package Entities.Abstract

import Entities.Interfaces.Competition
import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

abstract class Poll <S : ScoreMetrics, V : Vote>{

    abstract var pollAlgorithm : PollAlgorithm<S, V>
    abstract var competition : Competition<S>
    abstract var votesList : List<V>

    fun computePoll() : Ranking<S> {

            return pollAlgorithm.computeByAlgorithmRules(votesList)


    }
}