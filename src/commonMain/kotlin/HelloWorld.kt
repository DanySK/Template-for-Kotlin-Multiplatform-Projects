import Entities.Implementations.DescendingListOfPreferencesVote
import Entities.Interfaces.*
import Entities.Types.BestTimeInMatch
import Entities.Types.ConstantParameters.*
import Entities.Types.*
import Entities.Types.BestTimeInMatch.Companion.realized
import Entities.Types.WinsInCampionship.Companion.realized
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Application entrypoint.
 */
fun main() : Unit {
    println("Hello, ${Platform.name}!")
    /* val s1 = object : Score<BestTimeInMatch> {
        override val scoreValue: BestTimeInMatch
            get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
        override fun toString() : String = scoreValue.toString()
    }
    val s2 = object : Score<BestTimeInMatch> {
        override val scoreValue: BestTimeInMatch
            get() = BestTimeInMatch(20.toDuration(DurationUnit.DAYS))
        override fun toString() : String = scoreValue.toString()
    }

    val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

    val competitor2 = HumanCompetitor("Competitor 2", listOf(s2))

    val competition = object : Competition<BestTimeInMatch> {
        override val competitionName: String
            get() = "Sport match"
        override var competitors: List<Competitor<BestTimeInMatch>> = listOf(competitor1, competitor2)

    }

    val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
        override val votedCompetitor = competitor2
        override val voter: Voter = HumanVoter("Jacopo")

    }

    val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
        override val votedCompetitor = competitor2
        override val voter: Voter = HumanVoter("Federico1")

    }

    val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
        override val votedCompetitor = competitor1
        override val voter: Voter = HumanVoter("Federico2")

    }

    val votes = listOf(v1, v2, v3)*/
    /* val poll = PollSimulation(MajorityVotesAlgorithm(), competition, votes)

    DefaultPollManager(listOf(poll)).computeAllPolls().forEach { it.printRanking() }
*/


    val a = DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
        +poll {

            -competition {
                -"Race"
                +competitor {
                    -"compname1"
                    //+ (winsInChampionship realized (1))

                    + (BestTimeInMatch realized (1.toDuration(DurationUnit.HOURS)))

                }
                +competitor {
                    -"compname2"
                    + (BestTimeInMatch realized (2.toDuration(DurationUnit.HOURS)))


                }
            }
            -majorityVotesAlgorithm {
                +MultipleVotesAllowed
            }

            + ("compname1" votedBy "b")


            }
        }//.computeAllPolls()
        println(a)
        a.computeAllPolls().forEach { println(it.ranking) }



    val b = DefaultPollManager<WinsInCampionship, ListOfPreferencesVote<WinsInCampionship>>() initializedAs {
        +poll {


            -competition {
                -"Race"
                +competitor {
                    -"compname1"
                    + (WinsInCampionship realized 1)
                }
                +competitor {
                    -"compname2"
                    + (WinsInCampionship realized 1)
                }
            }

            - condorcetAlgorithm {
                +MultipleVotesAllowed
            }

            + ("compname1" then "compname2"  votedBy "b")

        }
    }//.computeAllPolls()
    println(b)
    b.computeAllPolls().forEach { println(it.ranking) }
    }



