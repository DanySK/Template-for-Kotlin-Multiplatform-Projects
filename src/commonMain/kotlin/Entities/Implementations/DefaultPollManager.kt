import Entities.Abstract.Poll
import Entities.Abstract.PollManager
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

class DefaultPollManager<S : ScoreMetrics, V : Vote>(override val polls: List<Poll<S, V>>) : PollManager<S, V>()