package entities.implementations

import entities.abstract.CompetitorAbstraction
import entities.interfaces.Competitor
import entities.interfaces.SinglePreferenceVote
import entities.interfaces.Voter
import entities.types.ConstantParameters
import entities.types.ScoreMetrics
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class MajorityVotesAlgorithmTests : StringSpec({

    "Majority Algorithm should throw exception when candidates are declared more than once" {

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val competitor21 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val candidates = listOf(competitor2, competitor21)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym2"
            }
        }
        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage<IllegalStateException>("Candidate already declared") {
            MajorityVotesAlgorithm<ScoreMetrics>()
                .apply { this.candidates = candidates.toList() }
                .computeByAlgorithmRules(votes)
        }
    }

    "Majority Algorithm should throw exception when multiple vote is not allowed" {

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val candidates = listOf(competitor2)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym2"
            }
        }
        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage<IllegalStateException>("Each voter can vote only once") {
            MajorityVotesAlgorithm<ScoreMetrics>()
                .apply { this.candidates = candidates.toList() }
                .computeByAlgorithmRules(votes)
        }
    }

    "Majority Algorithm should throw exception when MultipleVotesParameter is repeated more than once" {

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val candidates = listOf(competitor2)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym2"
            }
        }
        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage<IllegalStateException>("Parameter can't be repeated more than once") {
            MajorityVotesAlgorithm<ScoreMetrics>(
                listOf(
                    ConstantParameters.MultipleVotesAllowed,
                    ConstantParameters.MultipleVotesAllowed,
                ),
            )
                .apply { this.candidates = candidates.toList() }
                .computeByAlgorithmRules(votes)
        }
    }

    "Majority Algorithm should throw exception when vote is about a not allowed candidate" {

        val competitor1 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 1"
                this.scores = listOf()
            }

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val candidates = listOf(competitor1)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor1
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage<IllegalStateException>("Voted candidate doesn't exist as object") {
            MajorityVotesAlgorithm<ScoreMetrics>()
                .apply { this.candidates = candidates.toList() }
                .computeByAlgorithmRules(votes)
        }
    }

    "Majority Algorithm should throw exception when multiple vote is allowed but competitor is voted more than once" {

        val competitor1 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 1"
                this.scores = listOf()
            }

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val candidates = listOf(competitor1, competitor2)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor1
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val votes = listOf(v1, v2, v3)

        shouldThrowWithMessage<IllegalStateException>("Each voter can vote just once for each competitor") {
            MajorityVotesAlgorithm<ScoreMetrics>(listOf(ConstantParameters.MultipleVotesAllowed))
                .apply { this.candidates = candidates.toList() }
                .computeByAlgorithmRules(votes)
        }
    }

    "Majority Algorithm with 1 winner should have just 1 winner in the only placement" {

        val competitor1 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 1"
                this.scores = listOf()
            }

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val candidates = listOf(competitor1, competitor2)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym2"
            }
        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor1
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym3"
            }
        }

        val votes = listOf(v1, v2, v3)

        val ranking = MajorityVotesAlgorithm<ScoreMetrics>()
            .apply { this.candidates = candidates.toList() }
            .computeByAlgorithmRules(votes)
            .ranking

        ranking.size.shouldBe(1)

        val entry = ranking.entries.first()
        entry.value shouldBe 2
        entry.key.size.shouldBe(1)
        entry.key.first() shouldBe competitor2
    }

    "Majority Algorithm with 2 winner (tie) should have 2 winners in the only placement" {

        val competitor1 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 1"
                this.scores = listOf()
            }

        val competitor2 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 2"
                this.scores = listOf()
            }

        val competitor3 = object : CompetitorAbstraction<ScoreMetrics>() {}
            .apply {
                this.name = "Competitor 3"
                this.scores = listOf()
            }

        val candidates = listOf(competitor1, competitor2, competitor3)

        val v1 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym1"
            }
        }

        val v2 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor2
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym2"
            }
        }

        val v3 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor1
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym3"
            }
        }

        val v4 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor1
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym4"
            }
        }

        val v5 = object : SinglePreferenceVote<ScoreMetrics> {
            override var votedCompetitor: Competitor<ScoreMetrics> = competitor3
            override var voter: Voter = object : Voter {
                override val identifier: String = "anonym5"
            }
        }

        val votes = listOf(v1, v2, v3, v4, v5)

        val ranking = MajorityVotesAlgorithm<ScoreMetrics>()
            .apply { this.candidates = candidates.toList() }
            .computeByAlgorithmRules(votes)
            .ranking

        ranking.size.shouldBe(1)

        val entry = ranking.entries.first()
        entry.value shouldBe 2
        entry.key.size.shouldBe(2)
        entry.key.shouldContainAll(competitor1, competitor2)
    }
})
