package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.NumberOfVotes
import Entities.Types.Comparators
import Entities.Types.ScoreMetrics

class RankingByDescendingVotesThenHighestScore<S : ScoreMetrics>
    (unorderedRanking : Map<Competitor<S>, NumberOfVotes>) :
    RankingByDescendingVotes<S>(unorderedRanking) {
        init {
            if(super.ranking.any { it.first.scores.isEmpty() })
                throw IllegalStateException("Every competitor must have at least one score")
        }

    override val ranking: List<Pair<Competitor<S>, NumberOfVotes>>
        get() = super.ranking.sortedWith(Comparators.HighestScore())

}