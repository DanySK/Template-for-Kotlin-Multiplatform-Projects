package Implementations


import Entities.Abstract.Competitor
import Entities.Abstract.PollManager
import Entities.Interfaces.ListOfPreferencesVote
import Entities.Interfaces.SinglePreferenceVote
import Entities.Types.BestTimeInMatch
import Entities.Types.BestTimeInMatch.Companion.realized
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PollManagerTests : StringSpec ({

    "Poll simulation should return a ranking, computed with MajorityVotesAlgorithm" {
        val a = object : PollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() {} initializedAs {
            +poll {

                -competition {
                    -"Sport match"
                    +competitor {
                        -"Competitor 1"
                        +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))

                    }
                    +competitor {
                        -"Competitor 2"
                        +(BestTimeInMatch realized (20.toDuration(DurationUnit.HOURS)))


                    }
                }
                -majorityVotesAlgorithm {
                }

                +("Competitor 2" votedBy "J")
                +("Competitor 2" votedBy "F")
                +("Competitor 1" votedBy "G")
                +("Competitor 1" votedBy "V")


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
        entry.key.map { it.name }.shouldContainAll (competitor2.name, competitor1.name)


        shouldNotThrowAny { rankings.forEach { it.printRanking() } }
    }

    "Poll simulation should return a ranking, computed with MajorityVotesAndHighestScoreAlgorithm" {
            val a = object : PollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() {} initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"Competitor 1"
                            + (BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))

                        }
                        +competitor {
                            -"Competitor 2"
                            + (BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                            + (BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))


                        }
                        +competitor {
                            -"Competitor 3"
                            + (BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))


                        }
                    }
                    -majorityVotesHScoreAlgorithm {
                    }

                    + ("Competitor 2" votedBy "anonym1")
                    + ("Competitor 2" votedBy "anonym2")
                    + ("Competitor 1" votedBy "anonym3")
                    + ("Competitor 1" votedBy "anonym4")
                    + ("Competitor 3" votedBy "anonym5")
                    + ("Competitor 3" votedBy "anonym6")



                }
            }

            val ranking = a.computeAllPolls()

            ranking shouldHaveSize 1

            ranking.first().ranking shouldHaveSize 2

            val entries = ranking.first().ranking.entries.take(2)
            entries.map { it.value } shouldContainAll (listOf(2))
            entries[0].key.size shouldBe 2
            entries[1].key.size shouldBe 1

            val competitor1 = object : Competitor<BestTimeInMatch>() {}.apply {
                this.name = "Competitor 1"
                this.scores = listOf(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
            }
            val competitor2 = object : Competitor<BestTimeInMatch>() {}.apply {
                this.name = "Competitor 2"
                this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
            }
            val competitor3 = object : Competitor<BestTimeInMatch>() {}.apply {
                this.name = "Competitor 3"
                this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
            }
            entries[0].key.map{ it.name }.shouldContainAll (competitor2.name, competitor3.name)
            entries[1].key.map{ it.name}.shouldContainAll(competitor1.name)

            entries[0].key.map{ it.scores.first().scoreValue}.shouldContainAll (competitor2.scores.first().scoreValue,
                competitor3.scores.first().scoreValue)
            entries[1].key.map{ it.scores.first().scoreValue }.shouldContainAll (competitor1.scores.first().scoreValue)



    }

    "Poll simulation should return a ranking, computed with MajorityVotesAndLowestScoreAlgorithm" {
        val a = object : PollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() {} initializedAs {
            +poll {

                -competition {
                    -"Sport match"
                    +competitor {
                        -"Competitor 1"
                        + (BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))

                    }
                    +competitor {
                        -"Competitor 2"
                        + (BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        + (BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))


                    }
                    +competitor {
                        -"Competitor 3"
                        + (BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))


                    }
                }
                -majorityVotesLScoreAlgorithm {
                }

                + ("Competitor 2" votedBy "anonym1")
                + ("Competitor 2" votedBy "anonym2")
                + ("Competitor 1" votedBy "anonym3")
                + ("Competitor 1" votedBy "anonym4")
                + ("Competitor 3" votedBy "anonym5")
                + ("Competitor 3" votedBy "anonym6")



            }
        }

        val ranking = a.computeAllPolls()

        ranking shouldHaveSize 1
        ranking.first().ranking shouldHaveSize 2

        val entries = ranking.first().ranking.entries.take(2)
        entries.map { it.value } shouldContainAll (listOf(2))
        entries[0].key.size shouldBe 2
        entries[1].key.size shouldBe 1

        val competitor1 = object : Competitor<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 1"
            this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
        }
        val competitor2 = object : Competitor<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 2"
            this.scores = listOf(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
        }
        val competitor3 = object : Competitor<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 3"
            this.scores = listOf(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
        }

        entries[0].key.map{ it.name }.shouldContainAll (competitor2.name, competitor3.name)
        entries[1].key.map{ it.name}.shouldContainAll(competitor1.name)

        entries[0].key.map{ it.scores.first().scoreValue}.shouldContainAll (competitor2.scores.first().scoreValue,
            competitor3.scores.first().scoreValue)
        entries[1].key.map{ it.scores.first().scoreValue }.shouldContainAll (competitor1.scores.first().scoreValue)



    }

    "Poll simulation should return a ranking, computed with MyCondorcetAlgorithm"{
        var counter=0
        val a = object : PollManager<BestTimeInMatch, ListOfPreferencesVote<BestTimeInMatch>>() {} initializedAs {
            +poll {

                -competition {
                    -"Sport match"
                    +competitor {
                        -"C"
                    }


                    +competitor {
                        -"B"
                    }

                    +competitor {
                        -"A"
                    }

                }
                -condorcetAlgorithm {}

                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("A" then "C" then "B" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("B" then "C" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "B" then "A" votedBy "anonym"+counter++)
                + ("C" then "A" then "B" votedBy "anonym"+counter++)
                + ("C" then "A" then "B" votedBy "anonym"+counter++)


            }
        }

        val rankings = a.computeAllPolls()

        rankings shouldHaveSize 1

        val ranking = rankings.first().ranking
        ranking shouldHaveSize 3
        ranking.values shouldContainAll setOf(null)

        ranking.keys shouldBe setOf(setOf(object : Competitor<BestTimeInMatch>() {}.apply {
            this.name = "C"
            this.scores = listOf()
        }),
            setOf(object : Competitor<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            }),
            setOf(object : Competitor<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            }))
    }

})