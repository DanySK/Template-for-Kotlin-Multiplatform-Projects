package entities.interfaces

import entities.types.ScoreMetrics

/**
 * Interface which represents a vote composed by a list of preferences,
 * instead of a single one.
 */
interface ListOfPreferencesVote<S : ScoreMetrics> : Vote {
    /**
     * List of preferred competitors.
     */
    val votedCompetitors: List<Competitor<S>>
}
