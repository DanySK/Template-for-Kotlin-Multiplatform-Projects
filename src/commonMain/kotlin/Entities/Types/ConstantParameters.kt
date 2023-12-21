package Entities.Types

import Entities.Interfaces.PollAlgorithmParameter

sealed class ConstantParameters {


    data object MultipleVotesAllowed : PollAlgorithmParameter {
        override val parameter: String
            get() = "MultipleVotesAllowed"
    }
}