package Entities.Implementations

import Entities.Interfaces.Competition
import Entities.Interfaces.Competitor
import Entities.Types.ScoreMetrics

class SportCompetition<T : ScoreMetrics>(
    override val competitionName: String,
    override var competitors: List<Competitor<T>>
) : Competition<T>