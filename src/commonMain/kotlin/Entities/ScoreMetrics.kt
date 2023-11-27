import kotlin.time.Duration
import kotlin.time.DurationUnit

// interface ScoreMetrics
sealed class ScoreMetrics{
    data class BestTimeInMatch(val timing : Duration, val timingUnit : DurationUnit) : ScoreMetrics()
    data class WinsInCampionship(val wins : Int) : ScoreMetrics()
}