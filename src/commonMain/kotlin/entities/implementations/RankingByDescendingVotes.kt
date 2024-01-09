package entities.implementations

import entities.abstract.RankingAbstraction
import entities.interfaces.Competitor
import entities.types.ScoreMetrics
/**
 * Class which orders intermediate ranking by descending number of votes.
 */
open class RankingByDescendingVotes<S : ScoreMetrics>(private val unorderedRanking: Map<Competitor<S>, Int>) :
    RankingAbstraction<S>() {
    init {
        if (unorderedRanking.isEmpty()) error("Ranking cannot be empty")
    }

    override val ranking: Map<Set<Competitor<S>>, Int?>
        get() =
            unorderedRanking
                .toMapSetOfCompetitorsWithSameVoteNumber()
                .orderByHighestNumberOfVotes()

    private fun Map<Competitor<S>, Int>.toMapSetOfCompetitorsWithSameVoteNumber(): Map<Set<Competitor<S>>, Int> {
        return this.toList().groupBy { it.second }
            .map { (k, v) -> k to v.map { p -> p.first }.toSet() }
            .associate { (k, v) -> v to k }
            .toMap()
    }

    private fun Map<Set<Competitor<S>>, Int>.orderByHighestNumberOfVotes(): Map<Set<Competitor<S>>, Int> {
        val a =
            this
                .toList()
                .sortedByDescending { it.second }

        return a.toMap()
    }
}
