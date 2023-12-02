package Entities.Types

import Entities.Interfaces.Competitor
import Entities.Interfaces.NumberOfVotes
import Entities.Interfaces.Score

sealed class Comparators {

    class  HighestScore<S : ScoreMetrics> : Comparator<Pair<Competitor<S>, NumberOfVotes>> {

        override fun compare(a: Pair<Competitor<S>, NumberOfVotes>, b: Pair<Competitor<S>, NumberOfVotes>): Int {
            val bestOfA = a.first.scores.map { it.scoreValue }.max()
            val bestOfB = b.first.scores.map { it.scoreValue }.max()

            return bestOfB.compareTo(bestOfA)
        }
    }

    class  HighestNumberOfVotes<S : ScoreMetrics> : Comparator<Pair<Competitor<S>, NumberOfVotes>> {

        override fun compare(a: Pair<Competitor<S>, NumberOfVotes>, b: Pair<Competitor<S>, NumberOfVotes>): Int {

            val bestOfA = a.second
            val bestOfB = b.second

            return bestOfB.compareTo(bestOfA)
        }
    }
}