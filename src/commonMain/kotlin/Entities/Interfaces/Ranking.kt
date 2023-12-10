package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface Ranking<S : ScoreMetrics> {
    fun printRanking()

    val ranking : List<Pair<Competitor<S>, Int?>> //number of votes


}