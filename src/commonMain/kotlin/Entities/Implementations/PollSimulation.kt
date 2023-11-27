package Entities.Implementations

import Entities.Interfaces.*
import Entities.Types.ScoreMetrics

class PollSimulation<S : ScoreMetrics, V : Vote>(
    override val pollAlgorithm: PollAlgorithm<V>,
    override val competition: Competition<S>,
    override val votes: List<V>
) : Poll<S, V>