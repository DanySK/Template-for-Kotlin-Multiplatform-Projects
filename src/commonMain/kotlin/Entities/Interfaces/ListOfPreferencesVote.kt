package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface ListOfPreferencesVote<S : ScoreMetrics> : Vote {
    val votedCompetitors : List<Competitor<S>>
}