import Entities.Implementations.CompetitorVotesByDescendingRanking
import Entities.Interfaces.Competitor
import Entities.Interfaces.NumberOfVotes
import Entities.Types.ScoreMetrics
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotBeEmpty


class CompetitorVotesByDescendingRankingTests : StringSpec(
    {
        "CompetitorVotesByDescendingRanking cannot receive an empty ranking" {
            val exception = shouldThrow<IllegalArgumentException> {
                CompetitorVotesByDescendingRanking(emptyMap<Competitor<ScoreMetrics>, NumberOfVotes>())
            }
            exception.message.shouldNotBeBlank()
            exception.message.shouldNotBeEmpty()
            exception.message shouldBe ("Ranking cannot be empty")
        }
    },
)
