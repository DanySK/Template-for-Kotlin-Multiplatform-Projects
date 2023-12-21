package Implementations

import Entities.Implementation.MyCondorcetLikeRanking
import Entities.Implementations.HumanCompetitor
import Entities.Interfaces.Competitor
import Entities.Interfaces.Score
import Entities.Types.WinsInCampionship
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class MyCondorcetLikeRankingTests : StringSpec({

    "Ranking shouldn't have any number of votes"{
        val s1 = object : Score<WinsInCampionship> {
            override val scoreValue: WinsInCampionship
                get() = WinsInCampionship(1)

        }
        var m : List<Set<Competitor<WinsInCampionship>>> = listOf(
            setOf(HumanCompetitor("", listOf(s1)))
        )
        var r = MyCondorcetLikeRanking(m)

        r.ranking shouldHaveSize 1
        r.ranking.keys.first() shouldBe m.first()
        r.ranking.values.first() shouldBe null
    }

})