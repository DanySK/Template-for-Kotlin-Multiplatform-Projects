import Entities.Implementations.RankingByDescendingVotes
import Entities.Implementations.RankingByDescendingVotesThenHighestScore
import Entities.Implementations.HumanCompetitor
import Entities.Interfaces.Competitor
import Entities.Interfaces.NumberOfVotes
import Entities.Interfaces.Score
import Entities.Types.ScoreMetrics
import Entities.Types.WinsInCampionship
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotBeEmpty


class RankingByDescendingVotesTests : StringSpec(
    {
        "CompetitorRankingByDescendingVotes cannot receive an empty ranking" {
            val exception = shouldThrow<IllegalArgumentException> {
                RankingByDescendingVotes(emptyMap<Competitor<ScoreMetrics>, NumberOfVotes>())
            }
            exception.message.shouldNotBeBlank()
            exception.message.shouldNotBeEmpty()
            exception.message shouldBe ("Ranking cannot be empty")
        }

        "CompetitorRankingByDescendingVotes cannot have ascending ranking" {
            val map = mapOf<Competitor<WinsInCampionship>, NumberOfVotes>(
                HumanCompetitor("competitor 1", listOf<Score<WinsInCampionship>>()) to 1,
                HumanCompetitor("competitor 2", listOf<Score<WinsInCampionship>>()) to 2,
            )

            val ranking = RankingByDescendingVotes(map).ranking
            ranking.map { it.second } shouldNotBe listOf(1, 2)
            ranking.map { it.second } shouldBe listOf(2, 1)
        }

        "CompetitorRankingByDescendingVotes can have empty lists of scores" {

            val competitor2Score = object : Score<WinsInCampionship> {
                override val scoreValue: WinsInCampionship
                    get() = WinsInCampionship(1)
            }
            val map = mapOf<Competitor<WinsInCampionship>, NumberOfVotes>(
                HumanCompetitor("competitor 1", listOf<Score<WinsInCampionship>>()) to 1,
                HumanCompetitor("competitor 2", listOf<Score<WinsInCampionship>>(competitor2Score)) to 2,
            )

            shouldNotThrow<IllegalStateException> {
                RankingByDescendingVotes(map)
            }

        }

    }
)
