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



}


