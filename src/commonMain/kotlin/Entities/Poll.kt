interface Poll <T : ScoreMetrics>{
    val ranking : Ranking
    val pollAlgorithm : PollAlgorithm
    val competition : Competition<T>
    val votes : List<Vote>
}