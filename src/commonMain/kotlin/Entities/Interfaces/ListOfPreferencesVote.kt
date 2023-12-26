package Entities.Interfaces

import Entities.Abstract.Competitor
import Entities.Types.ScoreMetrics

interface ListOfPreferencesVote<S : ScoreMetrics> : Vote {
    val votedCompetitors : List<Competitor<S>>
}