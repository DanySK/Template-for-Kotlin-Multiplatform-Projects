package Entities.Implementations

import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.PollAlgorithmParameter
import Entities.Interfaces.Ranking
import Entities.Interfaces.SinglePreferenceVote
import Entities.Types.ScoreMetrics

class MajorityVotesAlgorithm<S : ScoreMetrics> : PollAlgorithm<S, SinglePreferenceVote<S>> {
    override val pollAlgorithmParameters = listOf<PollAlgorithmParameter>()
    override fun computeByAlgorithmRules(votes: List<SinglePreferenceVote<S>>): Ranking<S> {
        val votesCount = votes.groupingBy { it.votedCompetitor }.eachCount()

        val maxValue = votesCount.values.max()

        return RankingByDescendingVotes(votesCount.filterValues { it == maxValue })



    }
}