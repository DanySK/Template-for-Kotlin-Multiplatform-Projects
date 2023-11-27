interface Competition<T : ScoreMetrics> {
    val competitionName : String
    var competitors : List<Competitor<T>>
}