package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.DescendingListOfPreferencesVote
import Entities.Interfaces.Voter
import Entities.Types.ScoreMetrics

class DescendingListOfPreferencesVoteImpl<S : ScoreMetrics>(
    override val votedCompetitors: List<Competitor<S>>,
    override val voter: Voter
) : DescendingListOfPreferencesVote<S> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DescendingListOfPreferencesVoteImpl<*>

        return votedCompetitors == other.votedCompetitors
    }

    override fun hashCode(): Int {
        return votedCompetitors.hashCode()
    }
}