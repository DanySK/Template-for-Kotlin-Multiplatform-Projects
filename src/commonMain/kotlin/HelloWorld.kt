
import entities.implementations.DefaultPollManager
import entities.interfaces.ListOfPreferencesVote
import entities.interfaces.SinglePreferenceVote
import entities.types.BestTimeInMatch
import entities.types.BestTimeInMatch.Companion.realized
import entities.types.ConstantParameters
import entities.types.WinsInCampionship
import entities.types.WinsInCampionship.Companion.realized
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Main fun.
 */
fun main() {
    println("Hello, ${Platform.name}!")

    val a =
        DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
            +poll {
                -competition {
                    -"Race"
                    +competitor {
                        -"compname1"
                        +(BestTimeInMatch realized (1.toDuration(DurationUnit.HOURS)))
                    }

                    +competitor {
                        -"compname2"
                        +(BestTimeInMatch realized (2.toDuration(DurationUnit.HOURS)))
                    }
                }
                -majorityVotesAlgorithm {
                    +ConstantParameters.MultipleVotesAllowed
                }

                +("compname1" votedBy "b")
            }
        }
    println(a)
    a.computeAllPolls().forEach { println(it.ranking) }

    val b =
        DefaultPollManager<WinsInCampionship, ListOfPreferencesVote<WinsInCampionship>>() initializedAs {
            +poll {
                -competition {
                    -"Race"
                    +competitor {
                        -"compname1"
                        +(WinsInCampionship realized 1)
                    }
                    +competitor {
                        -"compname2"
                        +(WinsInCampionship realized 1)
                    }
                }

                -condorcetAlgorithm {
                    +ConstantParameters.MultipleVotesAllowed
                }

                +("compname1" then "compname2" votedBy "b")
            }
        } // .computeAllPolls()
    println(b)
    b.computeAllPolls().forEach { println(it.ranking) }
}
