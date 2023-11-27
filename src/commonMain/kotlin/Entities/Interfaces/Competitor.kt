package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface Competitor<S : ScoreMetrics>{
    val name : String
    var scores: List<Score<S>>
}