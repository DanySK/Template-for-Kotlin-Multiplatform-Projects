interface Competitor<T : ScoreMetrics>{
    val name : String
    var scores: List<Score<T>>
}