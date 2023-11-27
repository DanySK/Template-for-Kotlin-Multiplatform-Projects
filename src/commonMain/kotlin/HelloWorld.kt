import Entities.Implementations.HumanCompetitor
import Entities.Implementations.HumanVoter
import Entities.Implementations.MajorityAlgorithm
import Entities.Implementations.PollSimulation
import Entities.Interfaces.*
import Entities.Types.BestTimeInMatch
import Entities.Types.ScoreMetrics
import Entities.Types.WinsInCampionship
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Application entrypoint.
 */
fun main() {
    println("Hello, ${Platform.name}!")
    val s1 = object : Score<BestTimeInMatch> {
        override val scoreValue: BestTimeInMatch
            get() = BestTimeInMatch(1.toDuration(DurationUnit.DAYS))
        override fun toString() : String = scoreValue.toString()
    }
    val s2 = object : Score<BestTimeInMatch> {
        override val scoreValue: BestTimeInMatch
            get() = BestTimeInMatch(20.toDuration(DurationUnit.DAYS))
        override fun toString() : String = scoreValue.toString()
    }

    val competitor1 = HumanCompetitor("Competitor 1", listOf(s1))

    val competitor2 = HumanCompetitor("Competitor 2", listOf(s2))

    val competition = object : Competition<BestTimeInMatch> {
        override val competitionName: String
            get() = "Sport match"
        override var competitors: List<Competitor<BestTimeInMatch>> = listOf(competitor1, competitor2)

    }

    val v1 = object : SinglePreferenceVote<BestTimeInMatch> {
        override val votedCompetitor = competitor2
        override val voter: Voter = HumanVoter("Jacopo")

    }

    val v2 = object : SinglePreferenceVote<BestTimeInMatch> {
        override val votedCompetitor = competitor2
        override val voter: Voter = HumanVoter("Federico1")

    }

    val v3 = object : SinglePreferenceVote<BestTimeInMatch> {
        override val votedCompetitor = competitor1
        override val voter: Voter = HumanVoter("Federico2")

    }

    val votes = listOf(v1, v2, v3)
    val poll = PollSimulation(MajorityAlgorithm(), competition, votes)
    val ranking = poll.computePoll()
        ranking.printRanking()
//val a = DefaultPollManager(listOf(poll))

}


