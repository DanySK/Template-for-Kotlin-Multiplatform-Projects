package Entities.Types

import Entities.Interfaces.Score
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ComparatorsTest : StringSpec({

    "HighestScoreComparator should be a descending comparator" {
        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.HOURS))

        }
        val s2 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(2.toDuration(DurationUnit.HOURS))

        }
        val comparator =  Comparators.HighestScore<BestTimeInMatch>()
        comparator.compare(s1, s2) shouldBeLessThan 0
        comparator.compare(s2, s1) shouldBeGreaterThan 0
        comparator.compare(s1, s1) shouldBe 0

    }

   /* "HighestNumberOfVotesComparator should be a descending comparator"{
        val s1 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(1.toDuration(DurationUnit.HOURS))

        }
        val s2 = object : Score<BestTimeInMatch> {
            override val scoreValue: BestTimeInMatch
                get() = BestTimeInMatch(2.toDuration(DurationUnit.HOURS))

        }

        val competitors = mapOf<Set<Competitor<BestTimeInMatch>>, Int?>(
            setOf(HumanCompetitor("C1", listOf(s2))) to 2,
            setOf(HumanCompetitor("C2", listOf(s1))) to 1
        ).toList()

        val comparator = Comparators.HighestNumberOfVotes<BestTimeInMatch>()
        comparator.compare(competitors[0], competitors[1]) shouldBeGreaterThan 0
        comparator.compare(competitors[1], competitors[0]) shouldBeLessThan 0
        comparator.compare(competitors[1], competitors[1]) shouldBe 0

    }*/


})