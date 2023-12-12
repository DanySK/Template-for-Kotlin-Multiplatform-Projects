

import Entities.Implementations.HumanCompetitor
import Entities.Implementations.RankingByDescendingVotes
import Entities.Interfaces.Competitor
import Entities.Interfaces.Score
import Entities.Types.ScoreMetrics
import Entities.Types.WinsInCampionship
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
            val exception = shouldThrow<IllegalArgumentException> {
                RankingByDescendingVotes(emptyMap<Competitor<ScoreMetrics>, Int>())
            }
            exception.message.shouldNotBeBlank()
            exception.message.shouldNotBeEmpty()
            exception.message shouldBe ("Ranking cannot be empty")
        }

        "CompetitorRankingByDescendingVotes cannot have ascending ranking" {
            val map = mapOf<Competitor<WinsInCampionship>, Int>(
                HumanCompetitor("competitor 1", listOf<Score<WinsInCampionship>>()) to 1,
                HumanCompetitor("competitor 2", listOf<Score<WinsInCampionship>>()) to 2,
                HumanCompetitor("competitor 3", listOf<Score<WinsInCampionship>>()) to 2,
                HumanCompetitor("competitor 4", listOf<Score<WinsInCampionship>>()) to 2,
            )

            val ranking = RankingByDescendingVotes(map).ranking
            ranking.map { it.value } shouldNotBe listOf(1, 2)
            ranking.map { it.value } shouldBe listOf(2, 1)


            ranking.map { it.key }[0].shouldHaveSize(3)
            ranking.map { it.key.map { c -> c.name } }[0].
            shouldContainAll("competitor 3", "competitor 4", "competitor 2")

            ranking.map { it.key }[1].shouldHaveSize(1)
            ranking.map { it.key.map { c -> c.name } }[1].shouldContain("competitor 1")


        }

        "CompetitorRankingByDescendingVotes can have empty lists of scores" {

            val competitor2Score = object : Score<WinsInCampionship> {
                override val scoreValue: WinsInCampionship
                    get() = WinsInCampionship(1)
            }
            val map = mapOf<Competitor<WinsInCampionship>, Int>(
                HumanCompetitor("competitor 1", listOf<Score<WinsInCampionship>>()) to 1,
                HumanCompetitor("competitor 2", listOf<Score<WinsInCampionship>>(competitor2Score)) to 2,
            )

            shouldNotThrow<IllegalStateException> {
                RankingByDescendingVotes(map)
            }

        }

    }
)
