package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.Score
import Entities.Types.ScoreMetrics

data class HumanCompetitor<S : ScoreMetrics> (override val name: String,
                                         override var scores: List<Score<S>>): Competitor<S>


