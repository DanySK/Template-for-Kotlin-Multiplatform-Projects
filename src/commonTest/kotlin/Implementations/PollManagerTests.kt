package Implementations

import DefaultPollManager
import Entities.Abstract.Competitor
import Entities.Interfaces.SinglePreferenceVote
import Entities.Types.BestTimeInMatch
import Entities.Types.BestTimeInMatch.Companion.realized
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PollManagerTests : StringSpec ({

    "Poll simulation should return a ranking, computed with MajorityVotesAlgorithm" {
        val a = DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
            +poll {

                -competition {
                    -"Sport match"
                    +competitor {
                        -"Competitor 1"
                        //+ (winsInChampionship realized (1))

                        + (BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))

                    }
                    +competitor {
                        -"Competitor 2"
                        + (BestTimeInMatch realized (20.toDuration(DurationUnit.HOURS)))


                    }
                }
                -majorityVotesAlgorithm {
                }

                + ("Competitor 2" votedBy "J")
                + ("Competitor 2" votedBy "F")
                + ("Competitor 1" votedBy "G")
                + ("Competitor 1" votedBy "V")


            }
        }



        val rankings = a.computeAllPolls()

        rankings shouldHaveSize 1
        rankings.first().ranking shouldHaveSize 1
        val entry = rankings.first().ranking.entries.first()

        entry.value shouldBe 2

        entry.key shouldHaveSize 2

        val competitor1 = object : Competitor<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 1"
            this.scores = listOf(BestTimeInMatch realized (1.toDuration(DurationUnit.HOURS)))
        }
        val competitor2 = object : Competitor<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 2"
            this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.HOURS)))
        }
        entry.key.map { it.name }.shouldContainInOrder (competitor2.name, competitor1.name)


        shouldNotThrowAny { rankings.forEach { it.printRanking() } }
    }
})