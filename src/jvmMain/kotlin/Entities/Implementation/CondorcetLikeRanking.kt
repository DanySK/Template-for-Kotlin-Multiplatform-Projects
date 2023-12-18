package Entities.Implementation

import Entities.Abstract.Ranking
import Entities.Interfaces.Competitor
import Entities.Types.ScoreMetrics

class CondorcetLikeRanking<S : ScoreMetrics>(algOutput : List<Set<Competitor<S>>>) : Ranking<S>() {


    override val ranking: Map<Set<Competitor<S>>, Int?> = algOutput.associateWith { null }

     override fun printRanking() {
        println(ranking.toString())
    }
}