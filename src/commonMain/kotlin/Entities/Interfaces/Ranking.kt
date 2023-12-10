package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface Ranking<S : ScoreMetrics> {
    fun printRanking()

    val ranking : Map<Set<Competitor<S>>, Int?> //number of votes


}