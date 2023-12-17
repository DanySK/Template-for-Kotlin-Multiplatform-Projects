package Implementations

import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Implementations.MajorityVotesAndLowestScoreAlgorithm
import Entities.Interfaces.Score
import Entities.Interfaces.SinglePreferenceVote
import Entities.Interfaces.Voter
import Entities.Types.BestTimeInMatch
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MajorityVotesAndLowestScoreAlgorithmTests: StringSpec({


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

        val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s2))

        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val votes = listOf(v1, v2, v3)

        val ranking = MajorityVotesAndLowestScoreAlgorithm<BestTimeInMatch>().
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
            override val voter: Voter = HumanVoter("anonym")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val votes = listOf(v1, v2, v3)

        val ranking = MajorityVotesAndLowestScoreAlgorithm<BestTimeInMatch>().
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
            override val voter: Voter = HumanVoter("anonym")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val v4 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val v5 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym")

        }

        val votes = listOf(v1, v2, v3, v4, v5)

        val ranking = MajorityVotesAndLowestScoreAlgorithm<BestTimeInMatch>().
        computeByAlgorithmRules(votes).
        ranking

        ranking.size.shouldBe(2)

        val entries = ranking.entries.take(2)
        entries.map { it.value } shouldContainAll (listOf(2))
        entries[0].key.size shouldBe 1
        entries[1].key.size shouldBe 1

        entries[0].key.first() shouldBe competitor1
        entries[1].key.first() shouldBe competitor2
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

        val competitor1 = HumanCompetitor("Competitor 1", listOf(s2))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s2, s1))

        val competitor3 = HumanCompetitor("Competitor 3", listOf(s1))

        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("anonym")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val v4 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("anonym")

        }

        val v5 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym")

        }
        val v6= object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor3
            override val voter: Voter = HumanVoter("anonym")

        }
        val votes = listOf(v1, v2, v3, v4, v5, v6)

        val ranking = MajorityVotesAndLowestScoreAlgorithm<BestTimeInMatch>().
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