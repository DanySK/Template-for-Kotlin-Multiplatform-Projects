package Implementations

import Entities.Implementations.DescendingListOfPreferencesVote
import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Implementations.MyCondorcetAlgorithm
import Entities.Interfaces.ListOfPreferencesVote
import Entities.Types.BestTimeInMatch
import Entities.Types.ConstantParameters
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class MyCondorcetAlgorithmTests : StringSpec({

    "Algorithm should throw exception when multiple vote is not allowed"{

        val competitors =  setOf(
            HumanCompetitor<BestTimeInMatch>("A"), HumanCompetitor("C"),
            HumanCompetitor("B")
        )

        val c = MyCondorcetAlgorithm(competitors)
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("A"),
                HumanCompetitor("C"),
                HumanCompetitor("B"),

            ),
                voter = HumanVoter("V1")
            )

            l += dl
        }


        shouldThrowWithMessage<IllegalStateException>("Each voter can vote only once") {
        c.computeByAlgorithmRules(l)  }


    }

    "Algorithm should throw exception when multiple vote is allowed but list of preferences is voted more than once"{

        val competitors =  setOf(
            HumanCompetitor<BestTimeInMatch>("A"), HumanCompetitor("C"),
            HumanCompetitor("B")
        )

        val c = MyCondorcetAlgorithm(competitors, listOf(ConstantParameters.MultipleVotesAllowed))
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("A"),
                HumanCompetitor("C"),
                HumanCompetitor("B"),

                ),
                voter = HumanVoter("V1")
            )

            l += dl
        }
        shouldThrowWithMessage <IllegalStateException>("Each voter can vote just once for each list of preferences")
        {

            c.computeByAlgorithmRules(l)  }


    }

    "Algorithm should throw exceptions when a list of preferences contains a not allowed candidate"{
        val competitors =  setOf(
            HumanCompetitor<BestTimeInMatch>("A"), HumanCompetitor("C"),
            HumanCompetitor("B")
        )
        var counter = 0
        val c = MyCondorcetAlgorithm(competitors)
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("A"),
                HumanCompetitor("C"),
                HumanCompetitor("random"),    //error case

            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..19){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("B"),
                HumanCompetitor("C"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..16){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("B"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..2){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("A"),
                HumanCompetitor("B", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>("A list of preferences contains one o more not allowed candidate"){
            c.computeByAlgorithmRules(l)
        }


    }


    "Algorithm should throw exception when an allowed candidate is absent in a list of preferences"{
        val competitors =  setOf(
            HumanCompetitor<BestTimeInMatch>("A"), HumanCompetitor("C"),
            HumanCompetitor("B")
        )
        var counter = 0
        val c = MyCondorcetAlgorithm(competitors)
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("A"),
                HumanCompetitor("C"),
                //error case
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..19){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("B"),
                HumanCompetitor("C"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..16){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("B"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..2){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("A"),
                HumanCompetitor("B", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>("Every allowed candidate must be present in every list of preferences"){
            c.computeByAlgorithmRules(l)
        }


    }

    "Algorithm should throw exceptions when an allowed candidate is present more than once in a list of preferences"{
        val competitors =  setOf(
            HumanCompetitor<BestTimeInMatch>("A"), HumanCompetitor("C"),
            HumanCompetitor("B")
        )

        var counter = 0
        val c = MyCondorcetAlgorithm(competitors)
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("A"),
                HumanCompetitor("C"),
                HumanCompetitor("B"),
                HumanCompetitor("C"), // error case
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..19){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("B"),
                HumanCompetitor("C"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..16){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("B"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..2){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("A"),
                HumanCompetitor("B", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>("Every allowed candidate can be present only once in the list of competitors"){
            c.computeByAlgorithmRules(l)
        }


    }

    "Algorithm should not throw exceptions and produce ranking"{
        val competitors =  setOf(
            HumanCompetitor<BestTimeInMatch>("A"), HumanCompetitor("C"),
            HumanCompetitor("B")
        )
        var counter = 0
        val c = MyCondorcetAlgorithm(competitors)
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for(i in 1..23){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("A"),
                HumanCompetitor("C"),
                HumanCompetitor("B")
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..19){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("B"),
                HumanCompetitor("C"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..16){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("B"),
                HumanCompetitor("A", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }

        for(i in 1..2){
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>(votedCompetitors =
            listOf(
                HumanCompetitor("C"),
                HumanCompetitor("A"),
                HumanCompetitor("B", listOf())
            ),
                voter = HumanVoter("V"+counter++)
            )

            l += dl
        }


        val r = c.computeByAlgorithmRules(l)
        r.ranking shouldHaveSize 3
        r.ranking.values shouldContainAll setOf(null)
        r.ranking.keys shouldBe setOf(setOf(HumanCompetitor("C")),
            setOf(HumanCompetitor("A")),
            setOf(HumanCompetitor("B")))
    }

})