interface PollManager {
    val polls : List<Poll<ScoreMetrics>>
}
val a = object : Poll<ScoreMetrics.WinsInCampionship> {
    override val competition: Competition<ScoreMetrics.WinsInCampionship>
        get() = TODO("Not yet implemented")
    override val pollAlgorithm: PollAlgorithm
        get() = TODO("Not yet implemented")
    override val ranking: Ranking
        get() = TODO("Not yet implemented")
    override val votes: List<Vote>
        get() = TODO("Not yet implemented")
}
val b = object : Poll<ScoreMetrics.BestTimeInMatch> {
    override val competition: Competition<ScoreMetrics.BestTimeInMatch>
        get() = TODO("Not yet implemented")
    override val pollAlgorithm: PollAlgorithm
        get() = TODO("Not yet implemented")
    override val ranking: Ranking
        get() = TODO("Not yet implemented")
    override val votes: List<Vote>
        get() = TODO("Not yet implemented")
}
val l = listOf(a,b)