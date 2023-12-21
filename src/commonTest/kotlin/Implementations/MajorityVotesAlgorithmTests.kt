package Implementations

import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Implementations.MajorityVotesAlgorithm
import Entities.Interfaces.SinglePreferenceVote
import Entities.Interfaces.Voter
import Entities.Types.ConstantParameters
import Entities.Types.ScoreMetrics
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class MajorityVotesAlgorithmTests : StringSpec({

    "Majority Algorithm should throw exception when multiple vote is not allowed"{

        val competitor1 = HumanCompetitor<ScoreMetrics>("Competitor 1")

        val competitor2 = HumanCompetitor<ScoreMetrics>("Competitor 2")

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym1")

        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage<IllegalStateException>("Each voter can vote only once") { MajorityVotesAlgorithm<ScoreMetrics>().
        computeByAlgorithmRules(votes)  }


    }

    "Majority Algorithm should throw exception when multiple vote is allowed but competitor is voted more than once"{

        val competitor1 = HumanCompetitor<ScoreMetrics>("Competitor 1")

        val competitor2 = HumanCompetitor<ScoreMetrics>("Competitor 2")

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym1")

        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage <IllegalStateException>("Each voter can vote just once for each competitor"){
        MajorityVotesAlgorithm<ScoreMetrics>(listOf(ConstantParameters.MultipleVotesAllowed)).
        computeByAlgorithmRules(votes)  }


    }





    "Majority Algorithm with 1 winner should have just 1 winner in the only placement"{

        val competitor1 = HumanCompetitor<ScoreMetrics>("Competitor 1")

        val competitor2 = HumanCompetitor<ScoreMetrics>("Competitor 2")

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym3")

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
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym3")

        }

        val v4 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym4")

        }

        val v5 = object : SinglePreferenceVote<ScoreMetrics> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym5")

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