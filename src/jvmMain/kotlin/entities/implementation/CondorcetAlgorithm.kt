/*package Entities.Implementation

import Entities.Abstract.CompetitorAbstraction
import Entities.Abstract.RankingAbstraction
import Entities.Interfaces.ListOfPreferencesVote
import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.PollAlgorithmParameter
import Entities.Types.ScoreMetrics
import kondorcet.Ballot
import kondorcet.method.CondorcetMethod
import kondorcet.model.DefaultBallot
import kondorcet.model.getResult
import kondorcet.model.toPoll

class CondorcetAlgorithm<S : ScoreMetrics>() : PollAlgorithm<S, ListOfPreferencesVote<S>>{
    override var pollAlgorithmParameters = listOf<PollAlgorithmParameter>()
    override fun computeByAlgorithmRules(votes: List<ListOfPreferencesVote<S>>): RankingAbstraction<S> {

        val votesCompetitor = votes.map { it.votedCompetitors }.groupingBy { it }
            .eachCount().toMap()


        val ballots : Map<Ballot<CompetitorAbstraction<S>>, Int> =
            votesCompetitor.map { (k, v) -> k.map { setOf(it) } to v }.associate { (k, v) -> DefaultBallot(k) to v }


        val poll = ballots.toPoll()
        val result = poll.getResult(CondorcetMethod)

        return CondorcetLikeRanking(result.orderedCandidates)

    }

}*/
