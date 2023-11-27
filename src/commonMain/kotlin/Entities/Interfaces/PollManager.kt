package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface PollManager {
    val polls : List<Poll<ScoreMetrics, Vote>>
}