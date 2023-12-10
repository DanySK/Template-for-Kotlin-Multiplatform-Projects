package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.Ranking
import Entities.Types.Comparators
import Entities.Types.ScoreMetrics

open class RankingByDescendingVotes<S : ScoreMetrics>(unorderedRanking : Map<Competitor<S>, Int>) : Ranking<S> {
    init {
        if (unorderedRanking.isEmpty()) throw IllegalArgumentException("Ranking cannot be empty")
    }

    private val _unorderedRanking = unorderedRanking
    override val ranking: Map<Set<Competitor<S>>, Int?>
        get() =  _unorderedRanking.
                 toMapSetOfCompetitorsWithSameVoteNumber().
                 toList().
                 sortedWith(Comparators.HighestNumberOfVotes()).
                 toMap()

    private fun Map<Competitor<S>, Int>.toMapSetOfCompetitorsWithSameVoteNumber() : Map<Set<Competitor<S>>,Int>{
    return  this.
            toList().
            groupBy { it.second }.
            map { (k, v) -> v.map { it.first }.toSet() to k }.
            toMap()
    }
    override fun printRanking() {
        println(ranking.toString())
    }


}