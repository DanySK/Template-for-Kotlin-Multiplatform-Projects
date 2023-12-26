package Entities.Implementation

import Entities.Abstract.Competitor
import Entities.Abstract.Ranking
import Entities.Types.ScoreMetrics

class MyCondorcetLikeRanking<S : ScoreMetrics>(algOutput : List<Set<Competitor<S>>>) : Ranking<S>() {


    override val ranking: Map<Set<Competitor<S>>, Int?> = algOutput.associateWith { null }

}