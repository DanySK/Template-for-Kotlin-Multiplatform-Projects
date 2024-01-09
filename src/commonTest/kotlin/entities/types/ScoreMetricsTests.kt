package entities.types

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ScoreMetricsTests : StringSpec({

    "BestTimeInMatch should be comparable with same type" {
        val b1 = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
        val b2 = BestTimeInMatch(2.toDuration(DurationUnit.DAYS))

        shouldNotThrow<IllegalArgumentException> {
            b1.compareTo(b2)
        }
    }

    "BestTimeInMatch should not be comparable with a different type" {
        val b1 = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
        val b2 = 2

        shouldThrow<IllegalArgumentException> {
            b1.compareTo(b2)
        }
    }

    "BestTimeInMatch should be order by descending" {
        val b1 = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
        val b2 = BestTimeInMatch(2.toDuration(DurationUnit.DAYS))

        b1.compareTo(b2) shouldBeLessThan(0)
        b2.compareTo(b1) shouldBeGreaterThan 0
        b1.compareTo(b1) shouldBe 0
    }

    "WinsInChampionship should be comparable with same type" {
        val b1 = WinsInCampionship(1)
        val b2 = WinsInCampionship(2)

        shouldNotThrow<IllegalArgumentException> {
            b1.compareTo(b2)
        }
    }

    "WinsInChampionship should not be comparable with a different type" {
        val b1 = WinsInCampionship(1)
        val b2 = 2

        shouldThrow<IllegalArgumentException> {
            b1.compareTo(b2)
        }
    }

    "WinsInChampionship should be should be order by descending" {
        val b1 = WinsInCampionship(1)
        val b2 = WinsInCampionship(2)

        b1.compareTo(b2) shouldBeLessThan 0
        b2.compareTo(b1) shouldBeGreaterThan 0
        b1.compareTo(b1) shouldBe 0
    }
})
