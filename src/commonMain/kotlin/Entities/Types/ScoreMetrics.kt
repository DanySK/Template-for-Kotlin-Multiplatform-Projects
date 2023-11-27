package Entities.Types

import kotlin.time.Duration
import kotlin.time.DurationUnit

// interface Entities.Types.ScoreMetrics
open class ScoreMetrics
data class BestTimeInMatch(val timing : Duration) : ScoreMetrics() {
 //   override fun toString(): String = timing.toString()

}
data class WinsInCampionship(val wins : Int) : ScoreMetrics()
