package Entities.Implementations

import Entities.Abstract.Ranking
import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.PollAlgorithmParameter
import Entities.Interfaces.SinglePreferenceVote
import Entities.Types.ConstantParameters
import Entities.Types.ScoreMetrics

class MajorityVotesAlgorithm<S : ScoreMetrics>(override var pollAlgorithmParameters: List<PollAlgorithmParameter> = listOf()) : PollAlgorithm<S, SinglePreferenceVote<S>> {

    override fun computeByAlgorithmRules(votes: List<SinglePreferenceVote<S>>): Ranking<S> {

        if (votes.isEmpty()) throw IllegalArgumentException("Votes list cannot be empty")

        when (pollAlgorithmParameters.count { it == ConstantParameters.MultipleVotesAllowed }){
            0 -> {
                if(votes.groupingBy { it.voter }.eachCount().any { it.value > 1 }){
                    throw IllegalStateException("Each voter can vote only once")
                }
            }
            1 ->{
                //multiple vote allowed
                if(votes.groupingBy { Pair(it.votedCompetitor, it.voter)}.eachCount().any { it.value > 1 }){
                    throw IllegalStateException("Each voter can vote just once for each competitor")
                }
            }
            else -> throw IllegalArgumentException("Parameter can't be repeated more than once")
        }


        val votesCount = votes.groupingBy { it.votedCompetitor }.eachCount()

        val maxValue = votesCount.values.max()

        return RankingByDescendingVotes(votesCount.filterValues { it == maxValue })



    }

    operator fun PollAlgorithmParameter.unaryPlus(){
        pollAlgorithmParameters += this@unaryPlus
    }

}