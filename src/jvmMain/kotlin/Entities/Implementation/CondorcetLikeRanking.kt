package Entities.Implementation

import Entities.Interfaces.Competitor
import Entities.Interfaces.Ranking
import Entities.Types.ScoreMetrics

class CondorcetLikeRanking<S : ScoreMetrics>(algOutput : List<Set<Competitor<S>>>) : Ranking<S>  {

    override val ranking: List<Pair<Competitor<S>, Int?>> = algOutput.map { Pair(it.first(), null) }

    override fun printRanking() {
        println(ranking.toString())
    }
}