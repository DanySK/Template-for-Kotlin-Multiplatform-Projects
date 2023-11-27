package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface Score<T : ScoreMetrics>{
    val scoreValue : T


}