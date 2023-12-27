import Entities.Abstract.PollManager
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

    val a = object : PollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>(){} initializedAs {
        +poll {

            -competition {
                -"Race"
                +competitor {
                    -"compname1"
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
        }
        println(a)
        a.computeAllPolls().forEach { println(it.ranking) }



    val b = object : PollManager<WinsInCampionship, ListOfPreferencesVote<WinsInCampionship>>() {} initializedAs {
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



