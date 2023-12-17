package Entities.Types

import Entities.Interfaces.Score


sealed class Comparators {

    class  HighestScore<S : ScoreMetrics> : Comparator<Score<S>> {

       override fun compare(a: Score<S>, b: Score<S>): Int {
           val bestOfA = a.scoreValue
           val bestOfB = b.scoreValue

           return bestOfA.compareTo(bestOfB)
        }
    }

    /*class  HighestNumberOfVotes<S : ScoreMetrics> : Comparator<Pair<Set<Competitor<S>>, Int?>> {

        override fun compare(a: Pair<Set<Competitor<S>>, Int?>, b: Pair<Set<Competitor<S>>, Int?>): Int {
            val bestOfA = a.second
            val bestOfB = b.second
            if(bestOfA == null || bestOfB == null) throw NullPointerException("Cannot compare null number of votes")
            return bestOfA.compareTo(bestOfB)
        }
    }*/
}