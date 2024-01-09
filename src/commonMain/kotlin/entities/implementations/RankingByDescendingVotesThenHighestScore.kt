package entities.implementations

import entities.interfaces.Competitor
import entities.types.Comparators
import entities.types.ScoreMetrics
/**
 * Class which orders intermediate ranking by descending number of votes, then by highest score.
 */
class RankingByDescendingVotesThenHighestScore<S : ScoreMetrics>(unorderedRanking: Map<Competitor<S>, Int>) :
    RankingByDescendingVotes<S>(unorderedRanking) {
    init {

        val competitors = super.ranking.flatMap { it.key }
        if (competitors.map { it.scores }.any { it.isEmpty() }) {
            error("Every competitor must have at least one score")
        }
    }

    override val ranking: Map<Set<Competitor<S>>, Int?>
        get() =
            super.ranking.computeWithHighestScoreRule()

    private fun Map<Set<Competitor<S>>, Int?>.computeWithHighestScoreRule(): Map<Set<Competitor<S>>, Int?> {
        val m = mutableMapOf<Set<Competitor<S>>, Int?>()
        for ((competitorsSetWithSameVotesNumber, votes) in this) {
            val competitorWithRelativeHighestScore =
                competitorsSetWithSameVotesNumber
                    .associateWith { competitor -> competitor.scores.maxWith(Comparators.HighestScore()) }

            for ((competitor, highestScore) in competitorWithRelativeHighestScore) {
                competitor.scores = competitor.scores.filter { it == highestScore }.distinct()
            } // keep just highest score, list will be list of 1 element

            val a =
                competitorWithRelativeHighestScore.toList().groupBy { it.second.scoreValue }
                    .map { (k, v) -> k to v.map { p -> p.first }.toSet() }
                    .sortedByDescending { it.first } // useful for order of next cycle
                    .toMap()

            for ((_, competitors) in a) {
                m[competitors] = votes
            }
        }
        return m
    }
}
