package entities.implementations

import entities.abstract.CompetitorAbstraction
import entities.abstract.ScoreAbstraction
import entities.interfaces.Competitor
import entities.types.ScoreMetrics
import entities.types.WinsInCampionship
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotBeEmpty

class RankingByDescendingVotesTests : StringSpec(
    {
        "CompetitorRankingByDescendingVotes cannot receive an empty ranking" {
            val exception =
                shouldThrow<IllegalStateException> {
                    RankingByDescendingVotes(emptyMap<Competitor<ScoreMetrics>, Int>())
                }
            exception.message.shouldNotBeBlank()
            exception.message.shouldNotBeEmpty()
            exception.message shouldBe ("Ranking cannot be empty")
        }

        "CompetitorRankingByDescendingVotes cannot have ascending ranking" {
            val map = mapOf<Competitor<WinsInCampionship>, Int>(
                object : CompetitorAbstraction<WinsInCampionship>() {}
                    .apply {
                        this.name = "competitor 1"
                        this.scores = listOf()
                    } to 1,

                object : CompetitorAbstraction<WinsInCampionship>() {}
                    .apply {
                        this.name = "competitor 2"
                        this.scores = listOf()
                    } to 2,

                object : CompetitorAbstraction<WinsInCampionship>() {}
                    .apply {
                        this.name = "competitor 3"
                        this.scores = listOf()
                    } to 2,

                object : CompetitorAbstraction<WinsInCampionship>() {}
                    .apply {
                        this.name = "competitor 4"
                        this.scores = listOf()
                    } to 2,
            )

            val ranking = RankingByDescendingVotes(map).ranking
            ranking.map { it.value } shouldNotBe listOf(1, 2)
            ranking.map { it.value } shouldBe listOf(2, 1)

            ranking.map { it.key }[0].shouldHaveSize(3)
            ranking.map { it.key.map { c -> c.name } }[0]
                .shouldContainAll("competitor 3", "competitor 4", "competitor 2")

            ranking.map { it.key }[1].shouldHaveSize(1)
            ranking.map { it.key.map { c -> c.name } }[1].shouldContain("competitor 1")
        }

        "CompetitorRankingByDescendingVotes can have empty lists of scores" {

            val competitor2Score = object : ScoreAbstraction<WinsInCampionship>() {
                override var scoreValue: WinsInCampionship = WinsInCampionship(1)
            }
            val map = mapOf<Competitor<WinsInCampionship>, Int>(
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

            shouldNotThrow<IllegalStateException> {
                RankingByDescendingVotes(map)
            }
        }
    },
)
