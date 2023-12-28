package Entities.Implementations

import Entities.Abstract.Competitor
import Entities.Abstract.Ranking
import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.PollAlgorithmParameter
import Entities.Interfaces.SinglePreferenceVote
import Entities.Types.ConstantParameters
import Entities.Types.ScoreMetrics

class MajorityVotesAndLowestScoreAlgorithm<S : ScoreMetrics>(override var pollAlgorithmParameters: List<PollAlgorithmParameter> = listOf()) : PollAlgorithm<S, SinglePreferenceVote<S>> {

    lateinit var candidates: List<Competitor<S>>

    override fun computeByAlgorithmRules(votes: List<SinglePreferenceVote<S>>): Ranking<S> {

        if(candidates.groupingBy { it.name }.eachCount().any { it.value > 1 }){
            throw IllegalStateException("Candidate already declared")
        }


        if (votes.isEmpty()) throw IllegalArgumentException("Votes list cannot be empty")


        if (votes.map { it.votedCompetitor }.any { it !in candidates })
            throw IllegalStateException("Voted candidate doesn't exist as object")

        when (pollAlgorithmParameters.count { it == ConstantParameters.MultipleVotesAllowed }){
            0 -> {
                if(votes.groupingBy { it.voter.identifier }.eachCount().any { it.value > 1 }){
                    throw IllegalStateException("Each voter can vote only once")
                }
            }
            1 ->{
                //multiple vote allowed
                if(votes.groupingBy { Pair(it.votedCompetitor.name, it.voter.identifier)}.eachCount().any { it.value > 1 }){
                    throw IllegalStateException("Each voter can vote just once for each competitor")
                }
            }
            else -> throw IllegalArgumentException("Parameter can't be repeated more than once")
        }

        var votesCount = votes.groupingBy { it.votedCompetitor }.eachCount()

        val defaultNoneVotes = candidates.filter { c -> c !in votes.map { it.votedCompetitor } }.associateWith { 0 }
        //insert non-voted candidates as 0
        votesCount = votesCount + defaultNoneVotes

        val maxValue = votesCount.values.max()

        return RankingByDescendingVotesThenLowestScore(votesCount.filterValues { it == maxValue })
    }
}