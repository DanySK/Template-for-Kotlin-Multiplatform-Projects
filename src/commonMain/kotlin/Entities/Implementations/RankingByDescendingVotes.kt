package Entities.Implementations

import Entities.Abstract.Ranking
import Entities.Interfaces.Competitor
import Entities.Types.ScoreMetrics

open class RankingByDescendingVotes<S : ScoreMetrics>(unorderedRanking : Map<Competitor<S>, Int>) : Ranking<S>() {
    init {
        if (unorderedRanking.isEmpty()) throw IllegalArgumentException("Ranking cannot be empty")
    }

    private val _unorderedRanking = unorderedRanking
    override val ranking: Map<Set<Competitor<S>>, Int?>
        get() =  _unorderedRanking.
                 toMapSetOfCompetitorsWithSameVoteNumber().
                 orderByHighestNumberOfVotes()

    private fun Map<Competitor<S>, Int>.toMapSetOfCompetitorsWithSameVoteNumber() : Map<Set<Competitor<S>>,Int>{
     return this.toList().groupBy { it.second }
        .map { (k, v) -> k to v.map { p -> p.first }.toSet() }
        .associate { (k, v) -> v to k }
        .toMap()
    }


    private fun Map<Set<Competitor<S>>,Int>.orderByHighestNumberOfVotes() : Map<Set<Competitor<S>>, Int> {
        val a = this.
        toList().
        sortedByDescending { it.second }

        return a.toMap()
    }

}