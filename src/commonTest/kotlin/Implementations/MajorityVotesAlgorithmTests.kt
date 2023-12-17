package Implementations

import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Implementations.MajorityVotesAlgorithm
import Entities.Interfaces.SinglePreferenceVote
import Entities.Interfaces.Voter
import Entities.Types.ScoreMetrics
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class MajorityVotesAlgorithmTests : StringSpec({

    "Majority Algorithm with 1 winner should have just 1 winner in the only placement"{

        val competitor1 = HumanCompetitor<ScoreMetrics>("Competitor 1")

        val competitor2 = HumanCompetitor<ScoreMetrics>("Competitor 2")

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val votes = listOf(v1, v2, v3)

        val ranking = MajorityVotesAlgorithm<ScoreMetrics>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(1)

        val entry = ranking.entries.first()
        entry.value shouldBe 2
        entry.key.size.shouldBe(1)
        entry.key.first() shouldBe competitor2
    }

    "Majority Algorithm with 2 winner (tie) should have 2 winners in the only placement"{

        val competitor1 = HumanCompetitor<ScoreMetrics>("Competitor 1")

        val competitor2 = HumanCompetitor<ScoreMetrics>("Competitor 2")

        val competitor3 = HumanCompetitor<ScoreMetrics>("Competitor 3")

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val v4 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val v5 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym")

        }

        val votes = listOf(v1, v2, v3, v4, v5)

        val ranking = MajorityVotesAlgorithm<ScoreMetrics>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(1)

        val entry = ranking.entries.first()
        entry.value shouldBe 2
        entry.key.size.shouldBe(2)
        entry.key.shouldContainAll(competitor1, competitor2)

    }



})