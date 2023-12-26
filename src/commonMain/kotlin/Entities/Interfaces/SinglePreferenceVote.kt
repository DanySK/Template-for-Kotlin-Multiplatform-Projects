package Entities.Interfaces

import Entities.Abstract.Competitor
import Entities.Types.ScoreMetrics

interface SinglePreferenceVote<S : ScoreMetrics> : Vote {
    var votedCompetitor : Competitor<S>
}