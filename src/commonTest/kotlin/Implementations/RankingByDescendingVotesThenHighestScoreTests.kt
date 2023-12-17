package Implementations
import Entities.Implementations.HumanCompetitor
import Entities.Implementations.RankingByDescendingVotesThenHighestScore
import Entities.Interfaces.Competitor
import Entities.Interfaces.Score
import Entities.Types.WinsInCampionship
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotBeEmpty

class RankingByDescendingVotesThenHighestScoreTests : StringSpec({
    "CompetitorRankingByDescendingVotesThenHighestScore cannot have empty lists of scores" {

        val competitor2Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(1)
        }
        val map = mapOf<Competitor<WinsInCampionship>, Int>(
            HumanCompetitor("competitor 1", listOf<Score<WinsInCampionship>>()) to 1,
            HumanCompetitor("competitor 2", listOf<Score<WinsInCampionship>>(competitor2Score)) to 2,
        )

        val exception = shouldThrow<IllegalStateException> {
            RankingByDescendingVotesThenHighestScore(map)
        }
        exception.message.shouldNotBeBlank()
        exception.message.shouldNotBeEmpty()
        exception.message shouldBe ("Every competitor must have at least one score")

    }

    "CompetitorRankingByDescendingVotesThenHighestScore should put competitors in descending votes then with highest score first" {
        val competitor1Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(1)
        }
        val competitor2Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(1)
        }

        val competitor3m1Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(1)
        }

        val competitor3m2Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(10)
        }

        val competitor4m1Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(1)
        }

        val competitor4m2Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(10)
        }

        val competitor4m3Score = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(10)
        }

        val map = mapOf<Competitor<WinsInCampionship>, Int>(
            HumanCompetitor("competitor 1", listOf(competitor1Score)) to 1,
            HumanCompetitor("competitor 2", listOf(competitor2Score)) to 2,
            HumanCompetitor("competitor 3", listOf(competitor3m1Score,
                competitor3m2Score)) to 2,
            HumanCompetitor("competitor 4", listOf(competitor4m1Score,
                competitor4m2Score,competitor4m3Score)) to 2,
        )

        val ranking = RankingByDescendingVotesThenHighestScore(map).ranking

        ranking.map { it.value } shouldBe listOf(2, 2, 1)

        val scores = ranking.map { it.key.flatMap { competitor -> competitor.scores  }}
        scores.map { s -> s.map { it.scoreValue.wins } }  shouldBe listOf(listOf(10,10), listOf(1), listOf(1))

        val names = ranking.map { it.key.map { competitor -> competitor.name  }.toSet() }
        names shouldBe listOf(setOf("competitor 4", "competitor 3"), setOf("competitor 2"), setOf("competitor 1"))

    }
},
    )