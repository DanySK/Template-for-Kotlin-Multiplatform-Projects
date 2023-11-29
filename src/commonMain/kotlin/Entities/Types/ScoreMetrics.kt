package Entities.Types

import kotlin.time.Duration
import kotlin.time.DurationUnit

open class ScoreMetrics
data class BestTimeInMatch(val timing : Duration) : ScoreMetrics()
data class WinsInCampionship(val wins : Int) : ScoreMetrics()
