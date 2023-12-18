package Implementations

import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Implementations.MajorityVotesAndHighestScoreAlgorithm
import Entities.Interfaces.Score
import Entities.Interfaces.SinglePreferenceVote
import Entities.Interfaces.Voter
import Entities.Types.BestTimeInMatch
import Entities.Types.ConstantParameters
import Entities.Types.ScoreMetrics
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MajorityVotesAndHighestScoreAlgorithmTests : StringSpec({

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

        shouldThrowWithMessage<IllegalStateException>("Each voter can vote only once") { MajorityVotesAndHighestScoreAlgorithm<ScoreMetrics>().
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
            MajorityVotesAndHighestScoreAlgorithm<ScoreMetrics>(listOf(ConstantParameters.MultipleVotesAllowed)).
            computeByAlgorithmRules(votes)  }


    }

    "Scores -> no ties in votes, no ties in score should have just 1 winner in the only placement"{

        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }
        val s2 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(20.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }

        val competitor1 = HumanCompetitor("Competitor 1", listOf(s2))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s1))

        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym3")

        }

        val votes = listOf(v1, v2, v3)

        val ranking = MajorityVotesAndHighestScoreAlgorithm<BestTimeInMatch>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(1)

        val entry = ranking.entries.first()
        entry.value shouldBe 2
        entry.key.size.shouldBe(1)
        entry.key.first() shouldBe competitor2
    }

    "Scores -> no ties in votes, ties in score should have just 1 winner in the only placement"{
        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }


        val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s1))

        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym3")

        }

        val votes = listOf(v1, v2, v3)

        val ranking = MajorityVotesAndHighestScoreAlgorithm<BestTimeInMatch>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(1)

        val entry = ranking.entries.first()
        entry.value shouldBe 2
        entry.key.size.shouldBe(1)
        entry.key.first() shouldBe competitor2

    }

    "Scores -> ties in votes, no ties in score should have 2 winners in 2 placements" {
        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }
        val s2 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(20.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }

        val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s2))

        val competitor3 = HumanCompetitor("Competitor 3", listOf(s2))

        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym3")

        }

        val v4 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym4")

        }

        val v5 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym5")

        }

        val votes = listOf(v1, v2, v3, v4, v5)

        val ranking = MajorityVotesAndHighestScoreAlgorithm<BestTimeInMatch>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(2)

        val entries = ranking.entries.take(2)
        entries.map { it.value } shouldContainAll (listOf(2))
        entries[0].key.size shouldBe 1
        entries[1].key.size shouldBe 1

        entries[0].key.first() shouldBe competitor2
        entries[1].key.first() shouldBe competitor1
    }

    "Scores -> ties in votes, ties in score should have 3 winners, 2 in first placement, 1 in second placement" {
        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }
        val s2 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(20.toDuration(DurationUnit.DAYS))
            override fun toString() : String = scoreValue.toString()
        }

        val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s2, s1))

        val competitor3 = HumanCompetitor("Competitor 3", listOf(s2))

        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym1")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym2")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym3")

        }

        val v4 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym4")

        }

        val v5 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym5")

        }
        val v6= object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym6")

        }
        val votes = listOf(v1, v2, v3, v4, v5, v6)

        val ranking = MajorityVotesAndHighestScoreAlgorithm<BestTimeInMatch>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(2)

        val entries = ranking.entries.take(2)
        entries.map { it.value } shouldContainAll (listOf(2))
        entries[0].key.size shouldBe 2
        entries[1].key.size shouldBe 1

        entries[0].key.shouldContainAll (competitor2, competitor3)
        entries[1].key.first() shouldBe competitor1

    }




    })