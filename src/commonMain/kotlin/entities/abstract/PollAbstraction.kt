package entities.abstract

import entities.implementations.MajorityVotesAlgorithm
import entities.implementations.MajorityVotesAndHighestScoreAlgorithm
import entities.implementations.MajorityVotesAndLowestScoreAlgorithm
import entities.implementations.MyCondorcetAlgorithm
import entities.interfaces.Competition
import entities.interfaces.ListOfPreferencesVote
import entities.interfaces.Poll
import entities.interfaces.PollAlgorithm
import entities.interfaces.Ranking
import entities.interfaces.SinglePreferenceVote
import entities.interfaces.Vote
import entities.types.ScoreMetrics

/**
 *
 */
abstract class PollAbstraction<S : ScoreMetrics, V : Vote> : Poll<S, V> {
    override lateinit var pollAlgorithm: PollAlgorithm<S, V>
    override lateinit var competition: Competition<S>
    override lateinit var votesList: List<V>

    override fun computePoll(): Ranking<S> = pollAlgorithm.computeByAlgorithmRules(votesList)

    abstract override infix fun List<String>.then(s: String): List<String>

    abstract override infix fun String.then(s: String): List<String>

    abstract override infix fun List<String>.votedBy(voterIdentifier: String): ListOfPreferencesVote<S>

    abstract override infix fun String.votedBy(voterIdentifier: String): SinglePreferenceVote<S>

    private inline fun <reified A> Any.cast(): A? {
        if (this !is A) return null
        return this
    }

    override fun majorityVotesAlgorithm(
        algInit: PollAlgorithm<S, SinglePreferenceVote<S>>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>> {
        val a =
            MajorityVotesAlgorithm<S>()
                .apply {
                    this.candidates = this@PollAbstraction.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, SinglePreferenceVote<S>>>()!!
    }

    override fun majorityVotesHScoreAlgorithm(
        algInit: PollAlgorithm<S, SinglePreferenceVote<S>>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>> {
        val a =
            MajorityVotesAndHighestScoreAlgorithm<S>()
                .apply {
                    this.candidates = this@PollAbstraction.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, SinglePreferenceVote<S>>>()!!
    }

    override fun majorityVotesLScoreAlgorithm(
        algInit: PollAlgorithm<S, SinglePreferenceVote<S>>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>> {
        val a =
            MajorityVotesAndLowestScoreAlgorithm<S>()
                .apply {
                    this.candidates = this@PollAbstraction.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, SinglePreferenceVote<S>>>()!!
    }

    override fun condorcetAlgorithm(
        algInit: PollAlgorithm<S, ListOfPreferencesVote<S>>.() -> Unit,
    ): PollAlgorithm<S, ListOfPreferencesVote<S>> {
        val a =
            MyCondorcetAlgorithm<S>()
                .apply {
                    this.candidates = this@PollAbstraction.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, ListOfPreferencesVote<S>>>()!!
    }

    override operator fun PollAlgorithm<S, V>.unaryMinus() {
        this@PollAbstraction.pollAlgorithm = this@unaryMinus
    }

    override operator fun Competition<S>.unaryMinus() {
        this@PollAbstraction.competition = this@unaryMinus
    }

    override fun competition(compInit: Competition<S>.() -> Unit): Competition<S> {
        return object : CompetitionAbstraction<S>() {}
            .apply(compInit)
    }

    override operator fun V.unaryPlus() {
        if (!this@PollAbstraction::votesList.isInitialized) {
            this@PollAbstraction.votesList = listOf()
        }
        this@PollAbstraction.votesList += this@unaryPlus
    }
}
