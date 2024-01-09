package entities.implementations

import entities.abstract.CompetitorAbstraction
import entities.interfaces.ListOfPreferencesVote
import entities.interfaces.Voter
import entities.types.BestTimeInMatch
import entities.types.ConstantParameters
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class MyCondorcetAlgorithmTests : StringSpec({

    "Majority Algorithm should throw exception when candidates are declared more than once" {

        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )

        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply {
                this.candidates = competitors.toList()
                this.pollAlgorithmParameters = listOf(ConstantParameters.MultipleVotesAllowed)
            }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {

                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V1"
                    }
                }

            l += dl
        }
        shouldThrowWithMessage<IllegalStateException>("Candidate already declared") { c.computeByAlgorithmRules(l) }
    }

    "Algorithm should throw exception when multiple vote is not allowed" {

        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },

        )

        val c = MyCondorcetAlgorithm<BestTimeInMatch>().apply { this.candidates = competitors.toList() }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>().apply {
                votedCompetitors =
                    listOf(
                        object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                            this.name = "A"
                            this.scores = listOf()
                        },
                        object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                            this.name = "C"
                            this.scores = listOf()
                        },

                        object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                            this.name = "B"
                            this.scores = listOf()
                        },

                    )
                voter = object : Voter {
                    override val identifier: String = "V1"
                }
            }
            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>("Each voter can vote only once") {
            c.computeByAlgorithmRules(l)
        }
    }

    "Algorithm should throw exception when MultipleVotesParameter is repeated more than once" {

        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )

        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply {
                this.candidates = competitors.toList()
                this.pollAlgorithmParameters = listOf(
                    ConstantParameters.MultipleVotesAllowed,
                    ConstantParameters.MultipleVotesAllowed,
                )
            }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..1) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {

                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V1"
                    }
                }

            l += dl
        }
        shouldThrowWithMessage<IllegalStateException>("Parameter can't be repeated more than once") {

            c.computeByAlgorithmRules(l)
        }
    }

    "Algorithm should throw exception when multiple vote is allowed but list of preferences is voted more than once" {

        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )

        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply {
                this.candidates = competitors.toList()
                this.pollAlgorithmParameters = listOf(ConstantParameters.MultipleVotesAllowed)
            }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {

                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V1"
                    }
                }

            l += dl
        }
        shouldThrowWithMessage<IllegalStateException>("Each voter can vote just once for each list of preferences") {

            c.computeByAlgorithmRules(l)
        }
    }

    "Algorithm should throw exceptions when a list of preferences contains a not allowed candidate" {
        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )
        var counter = 0
        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply { this.candidates = competitors.toList() }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "random"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..19) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..16) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..2) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>(
            "A list of preferences contains one o more not allowed candidate",
        ) {
            c.computeByAlgorithmRules(l)
        }
    }

    "Algorithm should throw exception when an allowed candidate is absent in a list of preferences" {
        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )
        var counter = 0
        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply { this.candidates = competitors.toList() }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            // error case

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..19) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..16) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..2) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>(
            "Every allowed candidate must be present in every list of preferences",
        ) {
            c.computeByAlgorithmRules(l)
        }
    }

    "Algorithm should throw exceptions when an allowed candidate is present more than once in a list of preferences" {
        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )

        var counter = 0
        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply { this.candidates = competitors.toList() }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                            // error case
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..19) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..16) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..2) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        shouldThrowWithMessage<IllegalStateException>(
            "Every allowed candidate can be present only once in the list of competitors",
        ) {
            c.computeByAlgorithmRules(l)
        }
    }

    "Algorithm should not throw exceptions and produce ranking" {
        val competitors = listOf(
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "A"
                this.scores = listOf()
            },
            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "C"
                this.scores = listOf()
            },

            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                this.name = "B"
                this.scores = listOf()
            },
        )
        var counter = 0
        val c = MyCondorcetAlgorithm<BestTimeInMatch>()
            .apply { this.candidates = competitors.toList() }
        val l = mutableListOf<ListOfPreferencesVote<BestTimeInMatch>>()
        for (i in 1..23) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..19) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..16) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },
                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        for (i in 1..2) {
            val dl = DescendingListOfPreferencesVote<BestTimeInMatch>()
                .apply {
                    votedCompetitors =
                        listOf(
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "C"
                                this.scores = listOf()
                            },
                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "A"
                                this.scores = listOf()
                            },

                            object : CompetitorAbstraction<BestTimeInMatch>() {}.apply {
                                this.name = "B"
                                this.scores = listOf()
                            },
                        )

                    voter = object : Voter {
                        override val identifier: String = "V" + counter++
                    }
                }

            l += dl
        }

        val r = c.computeByAlgorithmRules(l)
        r.ranking shouldHaveSize 3
        r.ranking.values shouldContainAll setOf(null)
        r.ranking.keys shouldBe setOf(
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
