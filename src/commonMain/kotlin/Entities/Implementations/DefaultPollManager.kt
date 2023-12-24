
import Entities.Abstract.Poll
import Entities.Abstract.PollManager
import Entities.Implementations.PollSimulation
import Entities.Interfaces.Vote
import Entities.Types.ScoreMetrics

class DefaultPollManager<S : ScoreMetrics, V : Vote> : PollManager<S, V>(){
    override lateinit var pollList: List<Poll<S, V>>


    infix fun initializedAs(initializer: DefaultPollManager<S,V>.() -> Unit): DefaultPollManager<S,V>{
        return DefaultPollManager<S,V>().apply(initializer)
    }
    operator fun PollSimulation<S,V>.unaryPlus(){
        if(!this@DefaultPollManager::pollList.isInitialized)
            this@DefaultPollManager.pollList = listOf()
        this@DefaultPollManager.pollList += this@unaryPlus
    }
    fun poll(newPoll : PollSimulation<S,V>.() -> Unit): PollSimulation<S,V>{
        return PollSimulation<S,V>().apply(newPoll)
    }



}