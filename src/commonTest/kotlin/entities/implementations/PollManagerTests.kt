package entities.implementations

import entities.abstract.CompetitorAbstraction
import entities.interfaces.ListOfPreferencesVote
import entities.interfaces.SinglePreferenceVote
import entities.types.BestTimeInMatch
import entities.types.BestTimeInMatch.Companion.realized
import entities.types.ConstantParameters
import entities.types.WinsInCampionship
import entities.types.WinsInCampionship.Companion.realized
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PollManagerTests : StringSpec({

    "Should  be thrown exception when candidates are declared more than once, in any algorithm" {

        shouldThrowWithMessage<IllegalStateException>("Candidate already declared") {
            DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"Competitor 1"
                            +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        }
                        +competitor {
                            -"Competitor 1"
                            +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        }
                    }
                    -majorityVotesAlgorithm {}
                }
            }
        }
        shouldThrowWithMessage<IllegalStateException>("Candidate already declared") {
            DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"Competitor 1"
                            +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        }
                        +competitor {
                            -"Competitor 1"
                            +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        }
                    }
                    -majorityVotesHScoreAlgorithm {}
                }
            }
        }
        shouldThrowWithMessage<IllegalStateException>("Candidate already declared") {
            DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"Competitor 1"
                        }
                        +competitor {
                            -"Competitor 1"
                        }
                    }
                    -majorityVotesLScoreAlgorithm {}
                }
            }
        }
        shouldThrowWithMessage<IllegalStateException>("Candidate already declared") {
            DefaultPollManager<BestTimeInMatch, ListOfPreferencesVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"C"
                        }
                        +competitor {
                            -"C"
                        }
                    }
                    -condorcetAlgorithm {}
                }
            }
        }
    }

    "Should be thrown exception when singlepreference vote is about a not allowed candidate" {

        shouldThrowWithMessage<NoSuchElementException>("Voted candidate doesn't exist as object") {
            DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"Competitor 1"
                            +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        }
                        +competitor {
                            -"Competitor 2"
                            +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        }
                    }
                    -majorityVotesAlgorithm {}
                    +("a" votedBy "b")
                }
            }
        }
    }

    "Should be thrown exception when listofpreferences vote contains not allowed candidate" {
        shouldThrowWithMessage<IllegalStateException>(
            "A list of preferences contains one o more not allowed candidate",
        ) {
            DefaultPollManager<BestTimeInMatch, ListOfPreferencesVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"A"
                        }
                        +competitor {
                            -"C"
                        }
                    }
                    -condorcetAlgorithm {}
                    +("A" then "B" votedBy "AAA")
                }
            }
        }
    }

    "Should be thrown exception when listofpreferences vote doesn't contain every allowed candidate" {
        shouldThrowWithMessage<IllegalStateException>(
            "Every allowed candidate must be present in every list of preferences",
        ) {
            DefaultPollManager<BestTimeInMatch, ListOfPreferencesVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"A"
                        }
                        +competitor {
                            -"B"
                        }
                        +competitor {
                            -"C"
                        }
                    }
                    -condorcetAlgorithm {}
                    +("A" then "B" votedBy "AAA")
                }
            }
        }
    }

    "Should be thrown exception when listofpreferences vote contains same candidate more than once" {
        shouldThrowWithMessage<IllegalStateException>(
            "Every allowed candidate can be present only once in the list of competitors",
        ) {
            DefaultPollManager<BestTimeInMatch, ListOfPreferencesVote<BestTimeInMatch>>() initializedAs {
                +poll {

                    -competition {
                        -"Sport match"
                        +competitor {
                            -"A"
                        }
                        +competitor {
                            -"B"
                        }
                        +competitor {
                            -"C"
                        }
                    }
                    -condorcetAlgorithm {}
                    +("A" then "B" then "B" then "C" votedBy "AAA")
                }
            }
        }
    }

    "Poll simulation should return a ranking, computed with MajorityVotesAlgorithm" {
        val a = DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
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
                    +ConstantParameters.MultipleVotesAllowed
                }

                +("Competitor 2" votedBy "J")
                +("Competitor 2" votedBy "F")
                +("Competitor 1" votedBy "J")
                +("Competitor 1" votedBy "F")
            }
        }

        val rankings = a.computeAllPolls()

        rankings shouldHaveSize 1
        rankings.first().ranking shouldHaveSize 1
        val entry = rankings.first().ranking.entries.first()

        entry.value shouldBe 2

        entry.key shouldHaveSize 2

        val competitor1 = object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 1"
            this.scores = listOf(BestTimeInMatch realized (1.toDuration(DurationUnit.HOURS)))
        }
        val competitor2 = object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 2"
            this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.HOURS)))
        }
        entry.key.map { it.name }.shouldContainAll(competitor2.name, competitor1.name)

        shouldNotThrowAny { rankings.forEach { it.printRanking() } }
    }

    "Poll simulation should return a ranking, computed with MajorityVotesAndHighestScoreAlgorithm" {
        val a = DefaultPollManager<BestTimeInMatch, SinglePreferenceVote<BestTimeInMatch>>() initializedAs {
            +poll {

                -competition {
                    -"Sport match"
                    +competitor {
                        -"Competitor 1"
                        +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                    }
                    +competitor {
                        -"Competitor 2"
                        +(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
                        +(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
                    }
                    +competitor {
                        -"Competitor 3"
                        +(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
                    }
                }
                -majorityVotesHScoreAlgorithm {
                }

                +("Competitor 2" votedBy "anonym1")
                +("Competitor 2" votedBy "anonym2")
                +("Competitor 1" votedBy "anonym3")
                +("Competitor 1" votedBy "anonym4")
                +("Competitor 3" votedBy "anonym5")
                +("Competitor 3" votedBy "anonym6")
            }
        }

        val ranking = a.computeAllPolls()

        ranking shouldHaveSize 1

        ranking.first().ranking shouldHaveSize 2

        val entries = ranking.first().ranking.entries.take(2)
        entries.map { it.value } shouldContainAll (listOf(2))
        entries[0].key.size shouldBe 2
        entries[1].key.size shouldBe 1

        val competitor1 = object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 1"
            this.scores = listOf(BestTimeInMatch realized (1.toDuration(DurationUnit.DAYS)))
        }
        val competitor2 = object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 2"
            this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
        }
        val competitor3 = object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
            this.name = "Competitor 3"
            this.scores = listOf(BestTimeInMatch realized (20.toDuration(DurationUnit.DAYS)))
        }
        entries[0].key.map { it.name }.shouldContainAll(competitor2.name, competitor3.name)
        entries[1].key.map { it.name }.shouldContainAll(competitor1.name)

        entries[0].key.map { it.scores.first().scoreValue }.shouldContainAll(
            competitor2.scores.first().scoreValue,
            competitor3.scores.first().scoreValue,
        )
        entries[1].key.map { it.scores.first().scoreValue }.shouldContainAll(competitor1.scores.first().scoreValue)
    }

    "Poll simulation should return a ranking, computed with MajorityVotesAndLowestScoreAlgorithm" {
        val a = DefaultPollManager<WinsInCampionship, SinglePreferenceVote<WinsInCampionship>>() initializedAs {
            +poll {

                -competition {
                    -"Sport match"
                    +competitor {
                        -"Competitor 1"
                        +(WinsInCampionship realized 20)
                    }
                    +competitor {
                        -"Competitor 2"
                        +(WinsInCampionship realized 1)
                        +(WinsInCampionship realized 20)
                    }
                    +competitor {
                        -"Competitor 3"
                        +(WinsInCampionship realized 1)
                    }
                }
                -majorityVotesLScoreAlgorithm {
                }

                +("Competitor 2" votedBy "anonym1")
                +("Competitor 2" votedBy "anonym2")
                +("Competitor 1" votedBy "anonym3")
                +("Competitor 1" votedBy "anonym4")
                +("Competitor 3" votedBy "anonym5")
                +("Competitor 3" votedBy "anonym6")
            }
        }

        val ranking = a.computeAllPolls()

        ranking shouldHaveSize 1
        ranking.first().ranking shouldHaveSize 2

        val entries = ranking.first().ranking.entries.take(2)
        entries.map { it.value } shouldContainAll (listOf(2))
        entries[0].key.size shouldBe 2
        entries[1].key.size shouldBe 1

        val competitor1 = object : CompetitorAbstraction<WinsInCampionship>() {}.apply {
            this.name = "Competitor 1"
            this.scores = listOf((WinsInCampionship realized 20))
        }
        val competitor2 = object : CompetitorAbstraction<WinsInCampionship>() {}.apply {
            this.name = "Competitor 2"
            this.scores = listOf((WinsInCampionship realized 1))
        }
        val competitor3 = object : CompetitorAbstraction<WinsInCampionship>() {}.apply {
            this.name = "Competitor 3"
            this.scores = listOf((WinsInCampionship realized 1))
        }

        entries[0].key.map { it.name }.shouldContainAll(competitor2.name, competitor3.name)
        entries[1].key.map { it.name }.shouldContainAll(competitor1.name)

        entries[0].key.map { it.scores.first().scoreValue }.shouldContainAll(
            competitor2.scores.first().scoreValue,
            competitor3.scores.first().scoreValue,
        )
        entries[1].key.map { it.scores.first().scoreValue }.shouldContainAll(competitor1.scores.first().scoreValue)
    }

    "Poll simulation should return a ranking, computed with MyCondorcetAlgorithm" {
        var counter = 0
        val a = DefaultPollManager<BestTimeInMatch, ListOfPreferencesVote<BestTimeInMatch>>() initializedAs {
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

                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("A" then "C" then "B" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("B" then "C" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "B" then "A" votedBy "anonym"+counter++)
                +("C" then "A" then "B" votedBy "anonym"+counter++)
                +("C" then "A" then "B" votedBy "anonym"+counter++)
            }
        }

        val rankings = a.computeAllPolls()

        rankings shouldHaveSize 1

        val ranking = rankings.first().ranking
        ranking shouldHaveSize 3
        ranking.values shouldContainAll setOf(null)

        ranking.keys shouldBe setOf(
            setOf(
                object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                    this.name = "C"
                    this.scores = listOf()
                },
            ),
            setOf(
                object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                    this.name = "A"
                    this.scores = listOf()
                },
            ),
            setOf(
                object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                    this.name = "B"
                    this.scores = listOf()
                },
            ),
        )
    }
})
