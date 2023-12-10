package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface DescendingListOfPreferencesVote<S : ScoreMetrics> : Vote {
    val votedCompetitors : List<Competitor<S>>
}