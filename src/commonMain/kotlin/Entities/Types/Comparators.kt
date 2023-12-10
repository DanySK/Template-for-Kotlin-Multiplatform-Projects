package Entities.Types

import Entities.Interfaces.Competitor


sealed class Comparators {

    class  HighestScore<S : ScoreMetrics> : Comparator<Pair<Competitor<S>, Int?>> {

       override fun compare(a: Pair<Competitor<S>, Int?>, b: Pair<Competitor<S>, Int?>): Int {
           val bestOfA = a.first.scores.maxOfOrNull { it.scoreValue }
           val bestOfB = b.first.scores.maxOfOrNull { it.scoreValue }

           if(bestOfA == null || bestOfB == null) throw NullPointerException("Cannot compare null scores")
            return bestOfB.compareTo(bestOfA)
        }
    }

    class  HighestNumberOfVotes<S : ScoreMetrics> : Comparator<Pair<Set<Competitor<S>>, Int?>> {

        override fun compare(a: Pair<Set<Competitor<S>>, Int?>, b: Pair<Set<Competitor<S>>, Int?>): Int {
            val bestOfA = a.second
            val bestOfB = b.second
            if(bestOfA == null || bestOfB == null) throw NullPointerException("Cannot compare null number of votes")
            return bestOfB.compareTo(bestOfA)
        }
    }
}