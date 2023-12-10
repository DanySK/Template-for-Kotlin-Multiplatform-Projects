
import Entities.Implementation.CondorcetAlgorithm
import Entities.Implementations.DescendingListOfPreferencesVoteImpl
import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Interfaces.DescendingListOfPreferencesVote
import Entities.Types.BestTimeInMatch

fun main(){
       val c = CondorcetAlgorithm<BestTimeInMatch>()
        val l = mutableListOf<DescendingListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVoteImpl<BestTimeInMatch>(votedCompetitors =
            listOf(HumanCompetitor("A"),
                HumanCompetitor("C"),
                HumanCompetitor("B", listOf())),
                voter = HumanVoter("V$i"))

            l += dl
        }

        for(i in 1..19){
            val dl = DescendingListOfPreferencesVoteImpl<BestTimeInMatch>(votedCompetitors =
            listOf(HumanCompetitor("B"),
                HumanCompetitor("C"),
                HumanCompetitor("A", listOf())),
                voter = HumanVoter("V$i"))

            l += dl
        }

        for(i in 1..16){
            val dl = DescendingListOfPreferencesVoteImpl<BestTimeInMatch>(votedCompetitors =
            listOf(HumanCompetitor("C"),
                HumanCompetitor("B"),
                HumanCompetitor("A", listOf())),
                voter = HumanVoter("V$i"))

            l += dl
        }

        for(i in 1..2){
            val dl = DescendingListOfPreferencesVoteImpl<BestTimeInMatch>(votedCompetitors =
            listOf(HumanCompetitor("C"),
                HumanCompetitor("A"),
                HumanCompetitor("B", listOf())),
                voter = HumanVoter("V$i"))

            l += dl
        }


        val r = c.computeByAlgorithmRules(l)
            r.printRanking()
    }
