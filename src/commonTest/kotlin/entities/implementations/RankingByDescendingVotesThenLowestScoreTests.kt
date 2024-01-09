package entities.implementations
import entities.abstract.CompetitorAbstraction
import entities.abstract.ScoreAbstraction
import entities.interfaces.Competitor
import entities.types.WinsInCampionship
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotBeEmpty

class RankingByDescendingVotesThenLowestScoreTests : StringSpec(
    {
        "CompetitorRankingByDescendingVotesThenLowestScore cannot have empty lists of scores" {

            val competitor2Score =
                object : ScoreAbstraction<WinsInCampionship>() {
                    override var scoreValue: WinsInCampionship = WinsInCampionship(1)
                }
            val map =
                mapOf<Competitor<WinsInCampionship>, Int>(
                    object : CompetitorAbstraction<WinsInCampionship>() {}
                        .apply {
                            this.name = "competitor 1"
                            this.scores = listOf()
                        } to 1,
                    object : CompetitorAbstraction<WinsInCampionship>() {}
                        .apply {
                            this.name = "competitor 2"
                            this.scores = listOf(competitor2Score)
                        } to 2,
                )

            val exception =
                shouldThrow<IllegalStateException> {
                    RankingByDescendingVotesThenLowestScore(map)
                }
            exception.message.shouldNotBeBlank()
            exception.message.shouldNotBeEmpty()
            exception.message shouldBe ("Every competitor must have at least one score")
        }

        "CompetitorRankingByDescendingVotesThenLowestScore should put competitors in descending votes then with" +
            " lowest score first" {

                val competitor1Score =
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(1)
                    }
                val competitor2Score =
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(0)
                    }

                val competitor3m1Score =
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(5)
                    }

                val competitor3m2Score =
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(9)
                    }

                val competitor4m1Score =
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(5)
                    }

                val competitor4m2Score =
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(10)
                    }

                val competitor4m3Score =
                    @Suppress("ktlint:standard:multiline-expression-wrapping")
                    object : ScoreAbstraction<WinsInCampionship>() {
                        override var scoreValue: WinsInCampionship = WinsInCampionship(10)
                    }

                val map =
                    mapOf<Competitor<WinsInCampionship>, Int>(
                        object : CompetitorAbstraction<WinsInCampionship>() {}
                            .apply {
                                this.name = "competitor 1"
                                this.scores = listOf(competitor1Score)
                            } to 1,
                        object : CompetitorAbstraction<WinsInCampionship>() {}
                            .apply {
                                this.name = "competitor 2"
                                this.scores = listOf(competitor2Score)
                            } to 2,
                        object : CompetitorAbstraction<WinsInCampionship>() {}
                            .apply {
                                this.name = "competitor 3"
                                this.scores =
                                    listOf(
                                        competitor3m1Score,
                                        competitor3m2Score,
                                    )
                            } to 2,
                        object : CompetitorAbstraction<WinsInCampionship>() {}
                            .apply {
                                this.name = "competitor 4"
                                this.scores =
                                    listOf(
                                        competitor4m1Score,
                                        competitor4m2Score, competitor4m3Score,
                                    )
                            } to 2,
                    )

                val ranking = RankingByDescendingVotesThenLowestScore(map).ranking

                ranking.map { it.value } shouldBe listOf(2, 2, 1)

                val scores = ranking.map { it.key.flatMap { competitor -> competitor.scores } }
                scores.map { s -> s.map { it.scoreValue.wins } } shouldBe listOf(listOf(0), listOf(5, 5), listOf(1))

                val names = ranking.map { it.key.map { competitor -> competitor.name }.toSet() }

                names shouldBe
                    listOf(
                        setOf("competitor 2"), setOf("competitor 4", "competitor 3"),
                        setOf("competitor 1"),
                    )
            }
    },
)
