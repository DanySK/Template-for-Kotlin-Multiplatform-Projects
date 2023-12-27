package Entities.Abstract

import Entities.Types.ScoreMetrics

 abstract class Competition<T : ScoreMetrics> {
     lateinit var competitionName: String
     lateinit var competitors: List<Competitor<T>>




    operator fun String.unaryMinus(){
        this@Competition.competitionName = this@unaryMinus
    }

    operator fun Competitor<T>.unaryPlus(){
        if(!this@Competition::competitors.isInitialized)
            this@Competition.competitors = listOf()
        if(this@Competition.competitors.map { it.name }.contains(this.name))
            throw IllegalStateException("Candidate already declared")
        this@Competition.competitors += this
    }

    fun competitor(compInit: Competitor<T>.()-> Unit): Competitor<T> {
        return object : Competitor<T>(){
        }.apply(compInit)
    }

    /*override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Competition<*>

        if (competitionName != other.competitionName) return false
        if (competitors != other.competitors) return false

        return true
    }

    override fun hashCode(): Int {
        var result = competitionName.hashCode()
        result = 31 * result + competitors.hashCode()
        return result
    }
*/
    /*override fun toString(): String {
        return "Competition(competitionName='$competitionName', competitors=$competitors)"
    }*/


}


