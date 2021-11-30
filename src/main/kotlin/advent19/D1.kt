package advent19

import shared.AdventSolution
import shared.splitDoubles
import kotlin.math.floor

class D1 : AdventSolution(
    mapOf(
        "12" to 2,
        "14" to 2,
        "1969" to 654,
        "100756" to 33583
    ),
    mapOf(
        "14" to 2,
        "1969" to 966,
        "100756" to 50346
    ),
    "104489,69854,93424,103763,119636,130562,121744,84851,143661,94519,116576,148771,74038,131735,95594,125198,92217,84471,53518,97787,55422,137807,78806,74665,103930,121642,123008,104598,97383,129359,85372,88930,106944,118404,126095,67230,116697,85950,148291,123171,82736,52753,134746,53238,74952,105933,104613,115283,80320,139152,76455,66729,81209,130176,116843,67292,74262,131694,92817,51540,58957,143342,76896,129631,77148,129784,115307,96214,110538,51492,124376,78161,59821,58184,132524,130714,112653,137988,112269,62214,115989,123073,119711,82258,67695,81023,70012,93438,131749,103652,63557,88224,117414,75579,146422,139852,85116,124902,143167,147781"
) {
    override fun solveProblem1(input: String): Any {
        return input.splitDoubles().sumOf { it.requiredFuel() }
    }

    override fun solveProblem2(input: String): Any {
        return input.splitDoubles().sumOf { mass ->
            var totalFuelNeeded = 0
            var fuelToAdd = mass.requiredFuel()
            while (fuelToAdd > 0) {
                totalFuelNeeded += fuelToAdd
                fuelToAdd = fuelToAdd.requiredFuel()
            }
            totalFuelNeeded
        }
    }

    private fun Int.requiredFuel() = toDouble().requiredFuel()
    private fun Double.requiredFuel() = div(3).floor().toInt() - 2
    private fun Double.floor() = floor(this)

}
