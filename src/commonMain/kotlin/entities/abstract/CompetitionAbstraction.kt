package entities.abstract

import entities.interfaces.Competition
import entities.interfaces.Competitor
import entities.types.ScoreMetrics

/**
 *
 */
abstract class CompetitionAbstraction<T : ScoreMetrics> : Competition<T> {
    override lateinit var competitionName: String
    override lateinit var competitors: List<Competitor<T>>

    override operator fun String.unaryMinus() {
        this@CompetitionAbstraction.competitionName = this@unaryMinus
    }

    override operator fun Competitor<T>.unaryPlus() {
        if (!this@CompetitionAbstraction::competitors.isInitialized) {
            this@CompetitionAbstraction.competitors = listOf()
        }
        if (this@CompetitionAbstraction.competitors.map { it.name }.contains(this.name)) {
            error("Candidate already declared")
        }
        this@CompetitionAbstraction.competitors += this
    }

    override fun competitor(compInit: Competitor<T>.() -> Unit): Competitor<T> {
        return object : CompetitorAbstraction<T>() {
        }.apply(compInit)
    }
}
