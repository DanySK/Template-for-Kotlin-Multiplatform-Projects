package entities.implementations

import entities.abstract.RankingAbstraction
import entities.interfaces.Competitor
import entities.types.ScoreMetrics

/**
 * Represents a ranking, without associated number of votes.
 */
class MyCondorcetLikeRanking<S : ScoreMetrics>(algOutput: List<Set<Competitor<S>>>) : RankingAbstraction<S>() {
    override val ranking: Map<Set<Competitor<S>>, Int?> = algOutput.associateWith { null }
}
