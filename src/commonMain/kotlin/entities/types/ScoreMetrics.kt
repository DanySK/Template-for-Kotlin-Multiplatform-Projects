package entities.types
import entities.abstract.ScoreAbstraction
import entities.interfaces.Score
import kotlin.time.Duration

/**
 * Represents a metric useful two compare and evaluate results among competitors.
 */
abstract class ScoreMetrics : Comparable<Any>

/**
 * This class represents an abstraction for best time realized during a match.
 * @param duration time, with time unit associated
 */
data class BestTimeInMatch(val duration: Duration) : ScoreMetrics() {
    /**
     * Compare two objects.
     */
    override fun compareTo(other: Any): Int {
        require(other is BestTimeInMatch)
        return duration.compareTo(other.duration)
    }

    companion object {
        /**
         * Returns an object which implements ScoreScore<BestTimeInMatch>, given the duration.
         *  @param duration time, with time unit associated
         */
        infix fun Companion.realized(duration: Duration): Score<BestTimeInMatch> =
            object : ScoreAbstraction<BestTimeInMatch>() {
                override var scoreValue: BestTimeInMatch = BestTimeInMatch(duration)
            }
    }
}

/**
 * This class represents an abstraction for wins realized during a championship.
 * @param wins number of wins
 */

data class WinsInCampionship(val wins: Int) : ScoreMetrics() {
    /**
     * Compare two objects.
     */
    override fun compareTo(other: Any): Int {
        require(other is WinsInCampionship)
        return wins.compareTo(other.wins)
    }

    companion object {
        /**
         * Returns an object which implements Score<WinsInCampionship>, given the number of wins.
         *  @param wins number of wins
         */
        infix fun Companion.realized(wins: Int): Score<WinsInCampionship> =
            object : ScoreAbstraction<WinsInCampionship>() {
                override var scoreValue: WinsInCampionship = WinsInCampionship(wins)
            }
    }
}
