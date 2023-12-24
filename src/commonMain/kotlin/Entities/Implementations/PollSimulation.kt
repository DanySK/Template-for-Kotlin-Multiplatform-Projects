package Entities.Implementations

import Entities.Abstract.Poll
import Entities.Interfaces.*
import Entities.Types.ScoreMetrics

class PollSimulation<S : ScoreMetrics, V : Vote>: Poll<S, V>(){

    override lateinit var competition : Competition<S>
    override lateinit var votesList : List<V>
    override lateinit var pollAlgorithm: PollAlgorithm<S, V>

   // private var _tempList = listOf<V>()
    private inline fun <reified A> Any.cast(): A? {
        if (this !is A) return null
        return this
    }


    operator fun PollAlgorithm<S,V>.unaryMinus(){
        this@PollSimulation.pollAlgorithm = this@unaryMinus
    }
    fun majorityAlgorithm(algInit: MajorityVotesAlgorithm<S>.()->Unit) : PollAlgorithm<S,V>{
        val a = MajorityVotesAlgorithm<S>().apply(algInit)
        return a.cast<PollAlgorithm<S,V>>()!!

    }


    operator fun Competition<S>.unaryMinus(){
        this@PollSimulation.competition = this@unaryMinus
    }
    fun sportCompetition(compInit: SportCompetition<S>.()-> Unit) : Competition<S>{
        return SportCompetition<S>().apply(compInit)
    }


    /*operator fun List<V>.unaryMinus(){
        if(!this@PollSimulation::votesList.isInitialized)
            this@PollSimulation.votesList = _tempList
    }*/
    /*fun votes(votesInit: List<V>.()->Unit) : List<V>{
        return _tempList.apply(votesInit)
    }*/

    operator fun V.unaryPlus(){
        if(!this@PollSimulation::votesList.isInitialized)
            this@PollSimulation.votesList = listOf()
        this@PollSimulation.votesList += this@unaryPlus


    }

    infix fun String.votedBy(voterIdentifier : String) : SinglePreferenceVote<S>{

         return object : SinglePreferenceVote<S>{
             override var votedCompetitor: Competitor<S> =
                 this@PollSimulation.competition.competitors.firstOrNull() {
                     it.name == this@votedBy
                 }.let {
                     it ?: throw NoSuchElementException("Voted candidate doesn't exists in allowed candidates")
                 }

             override var voter: Voter =
                 HumanVoter(voterIdentifier)

         }
    }
    /*fun singlePreferenceVote(voteInit: SinglePreferenceVote<S>.()->Unit) : SinglePreferenceVote<S>{
        val a = object : SinglePreferenceVote<S>{
            override lateinit var votedCompetitor: Competitor<S>
            override lateinit var voter: Voter

        }

        return a.apply(voteInit)
    }*/

    /*fun descendingListOfPreferencesVote(voteInit: DescendingListOfPreferencesVote<S>.()->Unit) : DescendingListOfPreferencesVote<S>{
        val a =  DescendingListOfPreferencesVote<S>(listOf(),HumanVoter(""))

        return a.apply(voteInit)
    }*/



}

