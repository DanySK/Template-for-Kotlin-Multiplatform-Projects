/* Entities.Implementation

import Entities.Abstract.CompetitorAbstraction
import Entities.Abstract.RankingAbstraction
import Entities.Types.ScoreMetrics

class CondorcetLikeRanking<S : ScoreMetrics>(algOutput : List<Set<CompetitorAbstraction<S>>>)
: RankingAbstraction<S>() {


    override val ranking: Map<Set<CompetitorAbstraction<S>>, Int?> = algOutput.associateWith { null }

     override fun printRanking() {
        println(ranking.toString())
    }
}*/
