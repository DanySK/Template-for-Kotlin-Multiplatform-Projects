package entities.types

import entities.interfaces.PollAlgorithmParameter

/**
 * Contains value-independent parameters.
 */
sealed class ConstantParameters {
    /**
     * Given a competition and a voter, this type of parameter
     * represents the enabling to vote multiple times.
     */
    data object MultipleVotesAllowed : PollAlgorithmParameter {
        override val parameter: String
            get() = "MultipleVotesAllowed"
    }
}
