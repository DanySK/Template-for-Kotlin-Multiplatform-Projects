package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface SinglePreferenceVote<S : ScoreMetrics> : Vote {
    val votedCompetitor : Competitor<S>
}