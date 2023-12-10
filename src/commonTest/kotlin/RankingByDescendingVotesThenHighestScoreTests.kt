
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

        val map = mapOf<Competitor<WinsInCampionship>, Int>(
            HumanCompetitor("competitor 1", listOf(competitor1Score)) to 2,
            HumanCompetitor("competitor 2", listOf(competitor2Score)) to 1,
            HumanCompetitor("competitor 3", listOf(competitor3m1Score,
                competitor3m2Score)) to 2,
        )
        val ranking = RankingByDescendingVotesThenHighestScore(map).ranking
        ranking.map { it.value } shouldBe listOf(2, 2, 1)

        val scores = ranking.flatMap { it.key.flatMap { competitor -> competitor.scores  } }
        scores shouldBe listOf(competitor3m1Score,competitor3m2Score,
                               competitor1Score, competitor2Score)

        val names = ranking.flatMap { it.key.map { competitor -> competitor.name  } }
        names shouldBe listOf("competitor 3",
            "competitor 1",
            "competitor 2")

    }
},
    )