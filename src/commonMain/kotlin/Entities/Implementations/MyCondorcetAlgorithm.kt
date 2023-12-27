package Entities.Implementations

import Entities.Abstract.Competitor
import Entities.Abstract.Ranking
import Entities.Implementation.MyCondorcetLikeRanking
import Entities.Interfaces.ListOfPreferencesVote
import Entities.Interfaces.PollAlgorithm
import Entities.Interfaces.PollAlgorithmParameter
import Entities.Types.ConstantParameters
import Entities.Types.ScoreMetrics

class MyCondorcetAlgorithm<S : ScoreMetrics>(override var pollAlgorithmParameters: List<PollAlgorithmParameter> = listOf())  : PollAlgorithm<S, ListOfPreferencesVote<S>> {

    lateinit var candidates: List<Competitor<S>>

    override fun computeByAlgorithmRules(votes: List<ListOfPreferencesVote<S>>): Ranking<S> {

        if(candidates.groupingBy { it.name }.eachCount().any { it.value > 1 }){
            throw IllegalStateException("Candidate already declared")
        }

        if (votes.isEmpty()) throw IllegalArgumentException("Votes list cannot be empty")

        when (pollAlgorithmParameters.count { it == ConstantParameters.MultipleVotesAllowed }){
            0 -> {
                if(votes.groupingBy { it.voter.identifier }.eachCount().any { it.value > 1 }){
                    throw IllegalStateException("Each voter can vote only once")
                }
            }
            1 ->{
                //multiple vote allowed
                if(votes.groupingBy { Pair(it.votedCompetitors.map { c -> c.name }, it.voter.identifier)}.eachCount().any { it.value > 1 }){
                    throw IllegalStateException("Each voter can vote just once for each list of preferences")
                }
            }
            else -> throw IllegalArgumentException("Parameter can't be repeated more than once")
        }

        votes.map { it.votedCompetitors }.forEach {

            val setOfCompetitors = it.toSet()

            if (setOfCompetitors != candidates) { //mismatch between sets
                if((setOfCompetitors - candidates).isNotEmpty()){
                    throw IllegalStateException("A list of preferences contains one o more not allowed candidate")
                }
                if((candidates - setOfCompetitors).isNotEmpty()){
                    //every candidate must be present in the list of competitors
                    throw IllegalStateException("Every allowed candidate must be present in every list of preferences")
                }



            }

            val groupCount = it.groupingBy { comp -> comp }.eachCount()
            //every candidate can be present only once in the list of competitors
            if (groupCount.any { count -> count.value > 1 }) {
                throw IllegalStateException("Every allowed candidate can be present only once in the list of competitors")
            }
        }

        val result = calculateWinners(candidates.toList(), votes)

        return MyCondorcetLikeRanking(result)


    }


    private fun <S : ScoreMetrics> calculateVoteMatrix(
        candidates: List<Competitor<S>>,
        ballots: List<ListOfPreferencesVote<S>>
    ): Array<IntArray> {
        val numCandidates = candidates.size
        val voteMatrix = Array(numCandidates) { IntArray(numCandidates) }

        for (ballot in ballots) {
            for (i in 0 until numCandidates) {
                for (j in i + 1 until numCandidates) {
                    val candidate1 = candidates[i]
                    val candidate2 = candidates[j]


                    val indexCandidate1 = ballot.votedCompetitors.indexOfFirst {  it.name == candidate1.name  }//ballot.votedCompetitors.indexOf(candidate1)
                    val indexCandidate2 = ballot.votedCompetitors.indexOfFirst {  it.name == candidate2.name  }//ballot.votedCompetitors.indexOf(candidate2)

                    if (indexCandidate1 < indexCandidate2) {
                        voteMatrix[i][j]++
                    } else {
                        voteMatrix[j][i]++
                    }
                }
            }
        }

        return voteMatrix
    }

    private fun <S : ScoreMetrics> calculateWinners(
        candidatesList: List<Competitor<S>>,
        ballots: List<ListOfPreferencesVote<S>>
    ): List<Set<Competitor<S>>> {
        val voteMatrix = calculateVoteMatrix(candidatesList, ballots)
        val winners = mutableListOf<Set<Competitor<S>>>()
        val candidates = candidatesList.toMutableList()
        for (k in candidates.indices) {
            var maxVotes = 0
            var winningCandidate: Competitor<S>? = null


            for (i in candidates.indices) {
                var victories = 0
                var defeats = 0

                for (j in candidates.indices) {
                    if (i != j) {
                        if (voteMatrix[i][j] > voteMatrix[j][i]) {
                            victories++
                        } else if (voteMatrix[i][j] < voteMatrix[j][i]) {
                            defeats++
                        }
                    }
                }

                if (victories == candidates.size - 1 && defeats == 0) {
                    if (maxVotes <= voteMatrix[i].sum()) {
                        maxVotes = voteMatrix[i].sum()
                        winningCandidate = candidates[i]
                    }
                }
            }

            if (winningCandidate != null) {
                winners.add(setOf(winningCandidate))
                // set to 0 the preferences, in order to ignore it in next cycle
                for (i in candidates.indices) {
                    voteMatrix[i][candidates.indexOf(winningCandidate)] = 0
                }
                // remove actual winner from the list
                candidates.remove(winningCandidate)
            }
        }

        return winners.toList()
    }

}
