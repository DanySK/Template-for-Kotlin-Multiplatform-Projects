package entities.types

import entities.abstract.ScoreAbstraction
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ComparatorsTest : StringSpec({

    "HighestScoreComparator should be a descending comparator" {
        val s1 = object : ScoreAbstraction<BestTimeInMatch>() {
            override var scoreValue: BestTimeInMatch = BestTimeInMatch(1.toDuration(DurationUnit.HOURS))
        }
        val s2 = object : ScoreAbstraction<BestTimeInMatch>() {
            override var scoreValue: BestTimeInMatch = BestTimeInMatch(2.toDuration(DurationUnit.HOURS))
        }
        val comparator = Comparators.HighestScore<BestTimeInMatch>()
        comparator.compare(s1, s2) shouldBeLessThan 0
        comparator.compare(s2, s1) shouldBeGreaterThan 0
        comparator.compare(s1, s1) shouldBe 0
    }
})
