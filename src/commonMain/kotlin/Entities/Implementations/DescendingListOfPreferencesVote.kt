package Entities.Implementations

import Entities.Interfaces.Competitor
import Entities.Interfaces.ListOfPreferencesVote
import Entities.Interfaces.Voter
import Entities.Types.ScoreMetrics

class DescendingListOfPreferencesVote<S : ScoreMetrics>(
    override val votedCompetitors: List<Competitor<S>>,
    override val voter: Voter
) : ListOfPreferencesVote<S> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DescendingListOfPreferencesVote<*>

        return votedCompetitors == other.votedCompetitors
    }

    override fun hashCode(): Int {
        return votedCompetitors.hashCode()
    }
}