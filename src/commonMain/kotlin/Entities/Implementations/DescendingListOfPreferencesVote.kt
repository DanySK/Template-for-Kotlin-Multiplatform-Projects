package Entities.Implementations

import Entities.Abstract.Competitor
import Entities.Interfaces.ListOfPreferencesVote
import Entities.Interfaces.Voter
import Entities.Types.ScoreMetrics

class DescendingListOfPreferencesVote<S : ScoreMetrics>: ListOfPreferencesVote<S> {
    override lateinit var votedCompetitors: List<Competitor<S>>
    override lateinit var voter: Voter

   /* override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DescendingListOfPreferencesVote<*>

        return votedCompetitors == other.votedCompetitors
    }

    override fun hashCode(): Int {
        return votedCompetitors.hashCode()
    }*/

}