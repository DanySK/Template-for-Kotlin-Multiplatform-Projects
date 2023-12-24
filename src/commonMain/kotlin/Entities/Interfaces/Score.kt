package Entities.Interfaces

import Entities.Types.ScoreMetrics

interface Score<T : ScoreMetrics>{
    var scoreValue : T

    operator fun T.unaryMinus(){
        scoreValue = this
    }
}