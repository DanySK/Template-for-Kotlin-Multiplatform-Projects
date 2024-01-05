package entities.abstract

import entities.interfaces.Ranking
import entities.types.ScoreMetrics

/**
 *
 */
abstract class RankingAbstraction<S : ScoreMetrics> : Ranking<S> {
    override fun printRanking() = println(ranking.toString())
}
