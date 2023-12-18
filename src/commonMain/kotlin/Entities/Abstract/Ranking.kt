package Entities.Abstract

import Entities.Interfaces.Competitor
import Entities.Types.ScoreMetrics

abstract class Ranking<S : ScoreMetrics> {
    open fun printRanking() = println(ranking.toString())

    abstract val ranking : Map<Set<Competitor<S>>, Int?> //number of votes


}