package Entities.Abstract

import Entities.Interfaces.Score
import Entities.Types.ScoreMetrics

abstract class Competitor<S : ScoreMetrics> {
    lateinit var  name: String
    lateinit var scores: List<Score<S>>

     operator fun String.unaryMinus(){
         this@Competitor.name = this@unaryMinus
     }

     operator fun Score<S>.unaryPlus(){
         if(!this@Competitor::scores.isInitialized)
             this@Competitor.scores = listOf()
         this@Competitor.scores += this@unaryPlus
     }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Competitor<*>) return false

        if (name != other.name) return false
        if (scores != other.scores) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + scores.hashCode()
        return result
    }

    override fun toString(): String {
        return "Competitor(name='$name', scores=$scores)"
    }


}


