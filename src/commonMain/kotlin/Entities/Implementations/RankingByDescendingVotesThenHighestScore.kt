package Entities.Implementations

import Entities.Interfaces.Competitor

import Entities.Types.Comparators
import Entities.Types.ScoreMetrics

class RankingByDescendingVotesThenHighestScore<S : ScoreMetrics>
    (unorderedRanking : Map<Competitor<S>, Int>) :
    RankingByDescendingVotes<S>(unorderedRanking) {
        init {
            /*if(super.ranking.any { it.key.map { competitor -> competitor.scores }.isEmpty() })*/
            val competitors = super.ranking.flatMap { it.key }
            if(competitors.map { it.scores } .any { it.isEmpty() })
                throw IllegalStateException("Every competitor must have at least one score")

        }

    override val ranking: Map<Set<Competitor<S>>, Int?>
        get() =
            super.ranking.toFlattenCompetitorsAndVotes().toList().sortedWith(Comparators.HighestScore())
                .associate { setOf(it.first) to it.second }

    private fun Map<Set<Competitor<S>>, Int?>.toFlattenCompetitorsAndVotes() : Map<Competitor<S>,Int?>{
        val resultMap = mutableMapOf<Competitor<S>, Int?>()

        for ((competitors, value) in this) {
            for(competitor in competitors){
                if(!resultMap.containsKey(competitor)){
                    resultMap[competitor] = value
                }
            }

        }

        return resultMap
    }
}