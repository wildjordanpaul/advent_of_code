package advent21

import shared.AdventSolution
import shared.Point
import shared.splitInTwo
import kotlin.math.max

class Day17 : AdventSolution(
    mapOf("target area: x=20..30, y=-10..-5" to 45),
    mapOf("target area: x=20..30, y=-10..-5" to 112),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val (xRange, yRange) = input.toRanges()
        return Solver(xRange, yRange).bestTrajectory()
    }

    override fun solveProblem2(input: String): Any? {
        val (xRange, yRange) = input.toRanges()
        return Solver(xRange, yRange).allTrajectories().count()
    }

    private fun String.toRanges() = splitInTwo(", ") { xy ->
        val (start, end) = xy.splitInTwo("..") { ends ->
            ends.replace(Regex("[^0-9-]"), "").toInt()
        }
        start..end
    }

    data class Solver(val xRange: IntRange, val yRange: IntRange) {

        fun allTrajectories(): Set<Trajectory> {
            return allTrajectories(1..Int.MAX_VALUE) +
                    allTrajectories((Int.MIN_VALUE..0).reversed())
        }

        private fun allTrajectories(across: IntProgression): Set<Trajectory> {
            val trajectories = mutableSetOf<Trajectory>()
            var attempts = 0
            across.takeWhile { dy ->
                val newTrajectories = allTrajectories(dy)
                trajectories.addAll(newTrajectories)
                if(newTrajectories.isNotEmpty())
                    attempts = 0
                else if(trajectories.isNotEmpty())
                    attempts +=1
                attempts < 1000
            }
            return trajectories
        }

        private fun allTrajectories(dy: Int): Set<Trajectory> {
            val trajectories = mutableSetOf<Trajectory>()
            var failedAttempts = 0
            (1..Int.MAX_VALUE).forEach { dx ->
                val trajectory = Trajectory(dx, dy).run(xRange, yRange)
                if(trajectory.inRange(xRange, yRange)) {
                    failedAttempts = 0
                    trajectories.add(trajectory)
                } else {
                    failedAttempts += 1
                    if (failedAttempts > ATTEMPT_MAX)
                        return trajectories
                }
            }
            return trajectories
        }

        fun bestTrajectory(): Int? {
            var best: Trajectory? = null
            var attempts = 0
            (1..Int.MAX_VALUE).forEach { dy ->
                val trajectory = bestTrajectory(dy)
                val currentBest = best
                if (trajectory != null && (currentBest == null || trajectory.maxY > currentBest.maxY)) {
                    best = trajectory
                    attempts = 0
                    println("current best is ${trajectory.maxY}")
                } else if (currentBest != null) {
                    attempts +=1
                }

                if (attempts > 10) {
                    return best?.maxY
                }
            }
            return null
        }

        private fun bestTrajectory(dy: Int): Trajectory? {
            var currentBest: Trajectory? = null
            (1..Int.MAX_VALUE).forEach { dx ->
                val trajectory = Trajectory(dx, dy).run(xRange, yRange)
                if(trajectory.inRange(xRange, yRange)) {
                    val lastBest = currentBest
                    if(lastBest == null || lastBest.maxY < trajectory.maxY)
                        currentBest = trajectory
                    else return lastBest
                } else if(trajectory.passedXRange(xRange)) {
                    return null
                }
            }
            return null
        }
    }

    data class Trajectory(
        val dx: Int,
        val dy: Int
    ) {
        var location = Point(0,0)
        var currentDx = dx
        var currentDy = dy
        var maxY = 0

        fun run(xRange: IntRange, yRange: IntRange): Trajectory {
            while(shouldContinue(xRange, yRange)) {
                location = location.add(currentDx, currentDy)
                currentDx = if(currentDx > 0) currentDx - 1 else if (currentDx < 0) currentDx + 1 else 0
                currentDy -= 1
                maxY = max(location.y, maxY)
            }
            return this
        }

        fun inRange(xRange: IntRange, yRange: IntRange)= xRange.contains(location.x) && yRange.contains(location.y)
        fun shouldContinue(xRange: IntRange, yRange: IntRange) = !inRange(xRange, yRange) && !passedRange(xRange, yRange)
        fun passedXRange(xRange: IntRange) = xRange.last < location.x
        fun passedYRange(yRange: IntRange) = yRange.first > location.y
        fun passedRange(xRange: IntRange, yRange: IntRange) = passedXRange(xRange) || passedYRange(yRange)
    }

    companion object {
        const val ATTEMPT_MAX = 500
    }
}