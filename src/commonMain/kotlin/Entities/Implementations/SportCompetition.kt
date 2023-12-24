package Entities.Implementations

import Entities.Interfaces.Competition
import Entities.Interfaces.Competitor
import Entities.Types.ScoreMetrics

class SportCompetition<T : ScoreMetrics> : Competition<T> {
    override lateinit var competitionName: String
    override lateinit var competitors: List<Competitor<T>>




    operator fun String.unaryMinus(){
        this@SportCompetition.competitionName = this@unaryMinus
    }

    operator fun Competitor<T>.unaryPlus(){
        if(!this@SportCompetition::competitors.isInitialized)
            this@SportCompetition.competitors = listOf()
        this@SportCompetition.competitors += this
    }

    fun competitor(compInit: HumanCompetitor<T>.()-> Unit): HumanCompetitor<T>{
        return HumanCompetitor<T>().apply(compInit)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SportCompetition<*>

        if (competitionName != other.competitionName) return false
        if (competitors != other.competitors) return false

        return true
    }

    override fun hashCode(): Int {
        var result = competitionName.hashCode()
        result = 31 * result + competitors.hashCode()
        return result
    }

    override fun toString(): String {
        return "SportCompetition(competitionName='$competitionName', competitors=$competitors)"
    }


}


