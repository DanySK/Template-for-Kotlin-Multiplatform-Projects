package entities.interfaces

import entities.types.ScoreMetrics

/**
 * Interface that represents a vote for a single competitor.
 */
interface SinglePreferenceVote<S : ScoreMetrics> : Vote {
    /**
     * Competitor chosen in the vote.
     */
    var votedCompetitor: Competitor<S>
}
