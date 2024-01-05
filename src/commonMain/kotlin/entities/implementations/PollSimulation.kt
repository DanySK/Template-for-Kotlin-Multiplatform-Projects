package entities.implementations

import entities.abstract.CompetitionAbstraction
import entities.abstract.PollAbstraction
import entities.interfaces.Competition
import entities.interfaces.Competitor
import entities.interfaces.ListOfPreferencesVote
import entities.interfaces.PollAlgorithm
import entities.interfaces.SinglePreferenceVote
import entities.interfaces.Vote
import entities.interfaces.Voter
import entities.types.ScoreMetrics

/**
 * This class allows to create a poll with its mandatory members.
 */
class PollSimulation<S : ScoreMetrics, V : Vote> : PollAbstraction<S, V>() {
    override lateinit var votesList: List<V>

    private inline fun <reified A> Any.cast(): A? {
        if (this !is A) return null
        return this
    }

    /**
     * DSL-function which initializes [MajorityVotesAlgorithm].
     */
    fun majorityVotesAlgorithm(
        algInit: MajorityVotesAlgorithm<S>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>> {
        val a =
            MajorityVotesAlgorithm<S>()
                .apply {
                    this.candidates = this@PollSimulation.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, SinglePreferenceVote<S>>>()!!
    }

    /**
     * DSL-function which initializes [MajorityVotesAndHighestScoreAlgorithm].
     */
    fun majorityVotesHScoreAlgorithm(
        algInit: MajorityVotesAndHighestScoreAlgorithm<S>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>> {
        val a =
            MajorityVotesAndHighestScoreAlgorithm<S>()
                .apply {
                    this.candidates = this@PollSimulation.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, SinglePreferenceVote<S>>>()!!
    }

    /**
     * DSL-function which initializes [MajorityVotesAndLowestScoreAlgorithm].
     */
    fun majorityVotesLScoreAlgorithm(
        algInit: MajorityVotesAndLowestScoreAlgorithm<S>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>> {
        val a =
            MajorityVotesAndLowestScoreAlgorithm<S>()
                .apply {
                    this.candidates = this@PollSimulation.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, SinglePreferenceVote<S>>>()!!
    }

    /**
     * DSL-function which initializes [MyCondorcetAlgorithm].
     */
    fun condorcetAlgorithm(algInit: MyCondorcetAlgorithm<S>.() -> Unit): PollAlgorithm<S, ListOfPreferencesVote<S>> {
        val a =
            MyCondorcetAlgorithm<S>()
                .apply {
                    this.candidates = this@PollSimulation.competition.competitors
                }
                .apply(algInit)
        return a.cast<PollAlgorithm<S, ListOfPreferencesVote<S>>>()!!
    }

    /**
     * Shortcut which assigns the value to [pollAlgorithm].
     */
    operator fun PollAlgorithm<S, V>.unaryMinus() {
        this@PollSimulation.pollAlgorithm = this@unaryMinus
    }

    /**
     * Shortcut which assigns the value to [competition].
     */
    operator fun Competition<S>.unaryMinus() {
        this@PollSimulation.competition = this@unaryMinus
    }

    /**
     * DSL-function which initializes a [Competition].
     */
    fun competition(compInit: Competition<S>.() -> Unit): Competition<S> {
        return object : CompetitionAbstraction<S>() {}
            .apply(compInit)
    }

    /**
     * Shortcut to add a vote in [votesList].
     */
    operator fun V.unaryPlus() {
        if (!this@PollSimulation::votesList.isInitialized) {
            this@PollSimulation.votesList = listOf()
        }
        this@PollSimulation.votesList += this@unaryPlus
    }

    /**
     * This function allows a  shortcut to create a [SinglePreferenceVote],
     * given the name of [Competitor] voted by a [Voter], distinguished by its identifier.
     */
    infix fun String.votedBy(voterIdentifier: String): SinglePreferenceVote<S> {
        val comp =
            this@PollSimulation.competition.competitors.firstOrNull {
                it.name == this@votedBy
            } ?: throw NoSuchElementException("Voted candidate doesn't exist as object")

        return object : SinglePreferenceVote<S> {
            override var voter: Voter =
                object : Voter {
                    override val identifier: String
                        get() = voterIdentifier
                }

            override var votedCompetitor: Competitor<S> = comp
        }
    }

    /**
     * This function allows a  shortcut to create a [ListOfPreferencesVote],
     * given the name list of [Competitor] voted by a [Voter], distinguished by its identifier.
     */
    infix fun List<String>.votedBy(voterIdentifier: String): ListOfPreferencesVote<S> {
        if (this.isEmpty()) error("Votes list cannot be empty")

        val setOfCompetitors = this.toSet()
        val candidates = this@PollSimulation.competition.competitors.map { it.name }.toSet()

        if (setOfCompetitors != candidates) { // mismatch between sets
            if ((setOfCompetitors - candidates).isNotEmpty()) {
                error("A list of preferences contains one o more not allowed candidate")
            }
            if ((candidates - setOfCompetitors).isNotEmpty()) {
                // every candidate must be present in the list of competitors
                error("Every allowed candidate must be present in every list of preferences")
            }
        }
        val groupCount = this.groupingBy { it }.eachCount()
        // every candidate can be present only once in the list of competitors
        if (groupCount.any { count -> count.value > 1 }) {
            error("Every allowed candidate can be present only once in the list of competitors")
        }

        val listOfCompetitorObject = mutableListOf<Competitor<S>>()
        this.forEach { actualName ->

            listOfCompetitorObject +=
                this@PollSimulation.competition.competitors.firstOrNull { comp ->
                    comp.name == actualName
                }.let {
                    it ?: throw NoSuchElementException("Voted candidate doesn't exist as object")
                }
        }

        return DescendingListOfPreferencesVote<S>().apply {
            voter =
                object : Voter {
                    override val identifier: String
                        get() = voterIdentifier
                }
            votedCompetitors = listOfCompetitorObject
        }
    }
}
