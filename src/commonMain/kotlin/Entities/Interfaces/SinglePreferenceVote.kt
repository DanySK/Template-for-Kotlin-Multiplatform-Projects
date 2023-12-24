package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface SinglePreferenceVote<S : ScoreMetrics> : Vote {
    var votedCompetitor : Competitor<S>
}