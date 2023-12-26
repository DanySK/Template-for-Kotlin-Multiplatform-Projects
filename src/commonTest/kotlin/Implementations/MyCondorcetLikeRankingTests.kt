package Implementations

import Entities.Abstract.Competitor
import Entities.Implementation.MyCondorcetLikeRanking
import Entities.Interfaces.Score
import Entities.Types.WinsInCampionship
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class MyCondorcetLikeRankingTests : StringSpec({

    "Ranking shouldn't have any number of votes"{
        val s1 = object : Score<WinsInCampionship> {
            override var scoreValue: WinsInCampionship = WinsInCampionship(1)

        }

        val comp1 = object : Competitor<WinsInCampionship>(){}.apply {
            this.name = ""
            this.scores = listOf(s1)
        }
        val m : List<Set<Competitor<WinsInCampionship>>> = listOf(
            setOf(comp1)
        )
        val r = MyCondorcetLikeRanking(m)

        r.ranking shouldHaveSize 1
        r.ranking.keys.first() shouldBe m.first()
        r.ranking.values.first() shouldBe null
    }

})