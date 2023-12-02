package Entities.Implementations

import Entities.Abstract.Poll
import Entities.Interfaces.*
import Entities.Types.ScoreMetrics

class PollSimulation<S : ScoreMetrics, V : Vote>(
    override val pollAlgorithm: PollAlgorithm<S, V>,
    override val competition: Competition<S>,
    override val votes: List<V>
) : Poll<S, V>()