package Entities.Implementations

import Entities.Abstract.Competition
import Entities.Abstract.Competitor
import Entities.Abstract.Poll
import Entities.Interfaces.*
import Entities.Types.ScoreMetrics

class PollSimulation<S : ScoreMetrics, V : Vote>: Poll<S, V>(){

    override lateinit var competition : Competition<S>
    override lateinit var votesList : List<V>
    override lateinit var pollAlgorithm: PollAlgorithm<S, V>

    private inline fun <reified A> Any.cast(): A? {
        if (this !is A) return null
        return this
    }



    fun majorityVotesAlgorithm(algInit: MajorityVotesAlgorithm<S>.()->Unit) : PollAlgorithm<S,SinglePreferenceVote<S>>{
        val a = MajorityVotesAlgorithm<S>().
        apply {
            this.candidates = this@PollSimulation.competition.competitors
        }.
        apply(algInit)
        return a.cast<PollAlgorithm<S,SinglePreferenceVote<S>>>()!!

    }

    fun majorityVotesHScoreAlgorithm(algInit: MajorityVotesAndHighestScoreAlgorithm<S>.()->Unit) : PollAlgorithm<S,SinglePreferenceVote<S>>{
        val a = MajorityVotesAndHighestScoreAlgorithm<S>().
        apply {
            this.candidates = this@PollSimulation.competition.competitors
        }.
        apply(algInit)
        return a.cast<PollAlgorithm<S,SinglePreferenceVote<S>>>()!!

    }

    fun majorityVotesLScoreAlgorithm(algInit: MajorityVotesAndLowestScoreAlgorithm<S>.()->Unit) : PollAlgorithm<S,SinglePreferenceVote<S>>{
        val a = MajorityVotesAndLowestScoreAlgorithm<S>().
        apply {
            this.candidates = this@PollSimulation.competition.competitors
        }.
        apply(algInit)
        return a.cast<PollAlgorithm<S,SinglePreferenceVote<S>>>()!!

    }

    fun condorcetAlgorithm(algInit: MyCondorcetAlgorithm<S>.()->Unit) : PollAlgorithm<S,ListOfPreferencesVote<S>>{
        val a = MyCondorcetAlgorithm<S>().
        apply{
            this.candidates = this@PollSimulation.competition.competitors
        }.
        apply(algInit)
        return a.cast<PollAlgorithm<S,ListOfPreferencesVote<S>>>()!!

    }
    operator fun PollAlgorithm<S,V>.unaryMinus(){
        this@PollSimulation.pollAlgorithm = this@unaryMinus
    }

    operator fun Competition<S>.unaryMinus(){
        this@PollSimulation.competition = this@unaryMinus
    }
    fun competition(compInit: Competition<S>.()-> Unit) : Competition<S> {
        return object : Competition<S>(){}
            .apply(compInit)
    }


    operator fun V.unaryPlus(){
        if(!this@PollSimulation::votesList.isInitialized)
            this@PollSimulation.votesList = listOf()
        this@PollSimulation.votesList += this@unaryPlus


    }

    infix fun String.votedBy(voterIdentifier : String) : SinglePreferenceVote<S>{

        return object : SinglePreferenceVote<S>{
            override var votedCompetitor: Competitor<S> =
                this@PollSimulation.competition.competitors.firstOrNull {
                    it.name == this@votedBy
                }.let {
                    it ?: throw NoSuchElementException("Voted candidate doesn't exist as object")
                }

            override var voter: Voter = object : Voter{
                override val identifier: String
                    get() = voterIdentifier

            }


        }
    }

    infix fun List<String>.votedBy(voterIdentifier : String) : ListOfPreferencesVote<S> {

        if (this.isEmpty()) throw IllegalArgumentException("Votes list cannot be empty")

        val setOfCompetitors = this.toSet()
        val candidates = this@PollSimulation.competition.competitors.map { it.name }.toSet()


        if (setOfCompetitors != candidates) { //mismatch between sets
            if((setOfCompetitors - candidates).isNotEmpty()){
                throw IllegalStateException("A list of preferences contains one o more not allowed candidate")
            }
            if((candidates - setOfCompetitors).isNotEmpty()){
                //every candidate must be present in the list of competitors
                throw IllegalStateException("Every allowed candidate must be present in every list of preferences")
            }



        }
        val groupCount = this.groupingBy { it }.eachCount()
        //every candidate can be present only once in the list of competitors
        if (groupCount.any { count -> count.value > 1 }) {
            throw IllegalStateException("Every allowed candidate can be present only once in the list of competitors")
        }

        val listOfCompetitorObject = mutableListOf<Competitor<S>>()
        this.forEach { actualName ->

            listOfCompetitorObject += this@PollSimulation.competition.competitors.firstOrNull { comp ->
                comp.name == actualName
            }.let {
                it ?: throw NoSuchElementException("Voted candidate doesn't exist as object")
            }

        }


        return DescendingListOfPreferencesVote<S>().apply {
            voter = object : Voter{
                override val identifier: String
                    get() = voterIdentifier

            }
            votedCompetitors = listOfCompetitorObject
        }

    }


    infix fun List<String>.then(s: String)  = this + s
    infix fun String.then(s : String)  = listOf(this,s)







}

