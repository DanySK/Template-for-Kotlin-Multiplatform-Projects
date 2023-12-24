package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.Score
import Entities.Types.ScoreMetrics

class HumanCompetitor<S : ScoreMetrics>: Competitor<S> {
    override lateinit var  name: String
    override lateinit var scores: List<Score<S>>

     operator fun String.unaryMinus(){
         this@HumanCompetitor.name = this@unaryMinus
     }

     operator fun Score<S>.unaryPlus(){
         if(!this@HumanCompetitor::scores.isInitialized)
             this@HumanCompetitor.scores = listOf()
         this@HumanCompetitor.scores += this@unaryPlus
     }


    /*fun score(scoreInit: Score<S>.()-> Unit): Score<S>{
        val s = object : Score<S>{
            override lateinit var scoreValue: S
        }
        return s.apply(scoreInit)
    }*/

     override fun equals(other: Any?): Boolean {
         if (this === other) return true
         if (other == null || this::class != other::class) return false

         other as HumanCompetitor<*>

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
         return "HumanCompetitor(name='$name', scores=$scores)"
     }


 }


