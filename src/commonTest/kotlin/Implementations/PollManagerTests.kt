package Implementations

import DefaultPollManager
import Entities.Implementations.*
import Entities.Interfaces.Score
import Entities.Interfaces.SinglePreferenceVote
import Entities.Interfaces.Voter
import Entities.Types.BestTimeInMatch
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PollManagerTests : StringSpec ({

    "Poll simulation should return a ranking, computed with MajorityVotesAlgorithm" {
        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))

            override fun toString(): String = scoreValue.toString()
        }
        val s2 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(20.toDuration(DurationUnit.DAYS))

            override fun toString(): String = scoreValue.toString()
        }

        val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

        val competitor2 = HumanCompetitor("Competitor 2", listOf(s2))

        val competition = SportCompetition<BestTimeInMatch>("Sport match", listOf(competitor1, competitor2))


        val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("J")

        }

        val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor2
            override val voter: Voter = HumanVoter("F")

        }

        val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("G")

        }
        val v4 = object : SinglePreferenceVote<BestTimeInMatch> {
            override val votedCompetitor = competitor1
            override val voter: Voter = HumanVoter("V")

        }

        val votes = listOf(v1, v2, v3, v4)
        val polls = listOf(PollSimulation(MajorityVotesAlgorithm(), competition, votes))

        val rankings = DefaultPollManager(polls).computeAllPolls()

        rankings shouldHaveSize 1
        rankings.first().ranking shouldHaveSize 1
        val entry = rankings.first().ranking.entries.first()

        entry.value shouldBe 2

        entry.key shouldHaveSize 2
        entry.key.shouldContainAll (competitor1, competitor2)

        shouldNotThrowAny { rankings.forEach { it.printRanking() } }
    }
})