package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.NumberOfVotes
import Entities.Interfaces.Ranking
import Entities.Types.Comparators
import Entities.Types.ScoreMetrics

open class RankingByDescendingVotes<S : ScoreMetrics>(unorderedRanking : Map<Competitor<S>, NumberOfVotes>) : Ranking<S> {
    init {
        if (unorderedRanking.isEmpty()) throw IllegalArgumentException("Ranking cannot be empty")
    }

    private val _unorderedRanking = unorderedRanking
    override val ranking: List<Pair<Competitor<S>, NumberOfVotes>>
        get() =  _unorderedRanking.toList().sortedWith(Comparators.HighestNumberOfVotes())


    override fun printRanking() {
        println(ranking.toString())
    }


}