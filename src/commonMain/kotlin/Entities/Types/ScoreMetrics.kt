package Entities.Types

import Entities.Abstract.Score
import kotlin.time.Duration

abstract class ScoreMetrics : Comparable<Any>


data class BestTimeInMatch(val timing : Duration) : ScoreMetrics() {
    override fun compareTo(other: Any): Int {
        if(other is BestTimeInMatch){
            return timing.compareTo(other.timing)
        }
        else throw IllegalArgumentException("Compared value is not compatible")
    }

    companion object {
        infix fun Companion.realized(duration : Duration) : Score<BestTimeInMatch> =
            object : Score<BestTimeInMatch>() {
                 override var scoreValue: BestTimeInMatch = BestTimeInMatch(duration)
            }
    }


}






data class WinsInCampionship(val wins : Int) : ScoreMetrics() {
    override fun compareTo(other: Any): Int {
        if(other is WinsInCampionship){
            return wins.compareTo(other.wins)
        }
        else throw IllegalArgumentException("Compared value is not compatible")
    }

    companion object {
        infix fun Companion.realized(wins : Int) : Score<WinsInCampionship> =
            object : Score<WinsInCampionship>() {
                override var scoreValue: WinsInCampionship = WinsInCampionship(wins)
            }
    }



}
