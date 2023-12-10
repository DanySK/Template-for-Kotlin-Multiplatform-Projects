package Entities.Types

import Entities.Interfaces.Competitor


sealed class Comparators {

    class  HighestScore<S : ScoreMetrics> : Comparator<Pair<Competitor<S>, Int>> {

        override fun compare(a: Pair<Competitor<S>, Int>, b: Pair<Competitor<S>, Int>): Int {
            val bestOfA = a.first.scores.map { it.scoreValue }.max()
            val bestOfB = b.first.scores.map { it.scoreValue }.max()

            return bestOfB.compareTo(bestOfA)
        }
    }

    class  HighestNumberOfVotes<S : ScoreMetrics> : Comparator<Pair<Competitor<S>, Int>> {

        override fun compare(a: Pair<Competitor<S>, Int>, b: Pair<Competitor<S>, Int>): Int {

            val bestOfA = a.second
            val bestOfB = b.second

            return bestOfB.compareTo(bestOfA)
        }
    }
}