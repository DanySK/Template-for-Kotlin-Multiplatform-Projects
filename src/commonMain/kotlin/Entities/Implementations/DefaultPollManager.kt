import Entities.Interfaces.Poll
import Entities.Interfaces.PollManager
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

class DefaultPollManager(override val polls: List<Poll<ScoreMetrics, Vote>>) : PollManager