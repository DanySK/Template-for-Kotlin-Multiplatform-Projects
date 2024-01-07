package entities.interfaces

import entities.implementations.MajorityVotesAlgorithm
import entities.implementations.MajorityVotesAndHighestScoreAlgorithm
import entities.implementations.MajorityVotesAndLowestScoreAlgorithm
import entities.implementations.MyCondorcetAlgorithm
import entities.types.ScoreMetrics

/**
 * This inteface represents the poll to be evaluated.
 */
interface Poll<S : ScoreMetrics, V : Vote> {
    /**
     * Algorithm chosen to compute the final ranking.
     */
    var pollAlgorithm: PollAlgorithm<S, V>

    /**
     * Definition of competition and its members.
     */
    var competition: Competition<S>

    /**
     * Definition of votes.
     */
    var votesList: List<V>

    /**
     * Computes the final ranking.
     */
    fun computePoll(): Ranking<S>

    /**
     * DSL-function which initializes [MajorityVotesAlgorithm].
     */
    fun majorityVotesAlgorithm(
        algInit: PollAlgorithm<S, SinglePreferenceVote<S>>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>>

    /**
     * DSL-function which initializes [MajorityVotesAndHighestScoreAlgorithm].
     */
    fun majorityVotesHScoreAlgorithm(
        algInit: PollAlgorithm<S, SinglePreferenceVote<S>>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>>

    /**
     * DSL-function which initializes [MajorityVotesAndLowestScoreAlgorithm].
     */
    fun majorityVotesLScoreAlgorithm(
        algInit: PollAlgorithm<S, SinglePreferenceVote<S>>.() -> Unit,
    ): PollAlgorithm<S, SinglePreferenceVote<S>>

    /**
     * DSL-function which initializes [MyCondorcetAlgorithm].
     */
    fun condorcetAlgorithm(
        algInit: PollAlgorithm<S, ListOfPreferencesVote<S>>.() -> Unit,
    ): PollAlgorithm<S, ListOfPreferencesVote<S>>

    /**
     * Shortcut which assigns the value to [pollAlgorithm].
     */
    operator fun PollAlgorithm<S, V>.unaryMinus()

    /**
     * Shortcut which assigns the value to [competition].
     */
    operator fun Competition<S>.unaryMinus()

    /**
     * Shortcut to add a vote in [votesList].
     */
    operator fun V.unaryPlus()

    /**
     * DSL-function which initializes a [Competition].
     */
    fun competition(compInit: Competition<S>.() -> Unit): Competition<S>

    /**
     * Shortcut which allows competitors' name chaining in the list.
     */
    infix fun List<String>.then(s: String): List<String>

    /**
     * Shortcut which allows competitors' name chaining in the list.
     */
    infix fun String.then(s: String): List<String>

    /**
     * This function allows a  shortcut to create a [ListOfPreferencesVote],
     * given the name list of [Competitor] voted by a [Voter], distinguished by its identifier.
     */
    infix fun List<String>.votedBy(voterIdentifier: String): ListOfPreferencesVote<S>

    /**
     * This function allows a  shortcut to create a [SinglePreferenceVote],
     * given the name of [Competitor] voted by a [Voter], distinguished by its identifier.
     */
    infix fun String.votedBy(voterIdentifier: String): SinglePreferenceVote<S>
}
