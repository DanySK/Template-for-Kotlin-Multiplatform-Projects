package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.NumberOfVotes
import Entities.Interfaces.Ranking
import Entities.Types.ScoreMetrics

class CompetitorVotesByDescendingRanking<S : ScoreMetrics>(unorderedRanking : Map<Competitor<S>, NumberOfVotes>) : Ranking {
    init {
        if (unorderedRanking.isEmpty()) throw IllegalArgumentException("Ranking cannot be empty")
    }
    private val orderedByDescendingRanking : List<Pair<Competitor<S>, NumberOfVotes>> =
       unorderedRanking.toList().sortedByDescending { it.second }


    override fun printRanking() {
        println(orderedByDescendingRanking.toString())
    }

}