package days

import util.MutableMatrix
import util.Point
import kotlin.math.abs

class Day15 : Day(15) {
    override fun runPartOne(lines: List<String>): Any {
        val matrix = MutableMatrix.fromSingleDigits(lines) { c -> c.digitToInt() }
        val start = Point(0, 0)
        val optimal = aStar(matrix, start, matrix.max())
//        val optimal = optimalPath(matrix, Path(listOf(start), 0), setOf(start), matrix.max())
        return optimal!!.cost
    }

    /**
     * Estimated cost to reach end from p
     * ....p...
     * ....9...
     * ....9...
     * ....999e
     */
    fun heuristicCost(p: Point, end: Point): Int {
        val xDistance = abs(p.x - end.x)
        val yDistance = abs(p.y - end.y)
        return xDistance * 1 + yDistance * 1 - 1 // 1 less for the corner/end itself
    }

    fun aStar(matrix: MutableMatrix<Int>, start: Point, end: Point): Path? {
        // Basing myself on Wikipedia, because it's been a while...
        // https://en.wikipedia.org/wiki/A*_search_algorithm
        val openSet = mutableSetOf<Point>(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
        gScore[start] = 0

        val fScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
        fScore[start] = heuristicCost(start, end)

        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { fScore.getValue(it) }!!
            if (current == end) {
                val path = reconstructPath(cameFrom, current)
                matrix.print(hightlight = { p -> path.contains(p)})
                return Path(path, gScore[end]!!)
            }
//            matrix.print(hightlight = { p -> p == current})

            openSet.remove(current)
            val neighbours = current.cross().filter { matrix.contains(it) }
            neighbours.forEach { neighbour ->
                val tentativeGScore = gScore[current]!! + matrix.get(neighbour)
                if (tentativeGScore < gScore.getValue(neighbour)) {
                    cameFrom[neighbour] = current
                    gScore[neighbour] = tentativeGScore
                    fScore[neighbour] = tentativeGScore + heuristicCost(neighbour, end)
                    openSet.add(neighbour)
                }
            }
        }
        error("should not reach this...")
    }

    private fun reconstructPath(cameFrom: MutableMap<Point, Point>, end: Point): List<Point> {
        val path = mutableListOf(end)
        while (cameFrom.contains(path.last())) {
            path.add(cameFrom[path.last()]!!)
        }
        return path.reversed()
    }

    fun optimalPath(matrix: MutableMatrix<Int>, path: Path, forbidden: Set<Point>, end: Point): Path? {
        val current = path.lastPoint()
//        val candidates = current.cross()
        val candidates = listOf(current.right(), current.down())
        if (candidates.contains(end)) {
            return path.add(end, matrix.get(end))
        }
        val allowedCandidates = candidates.subtract(forbidden)
            .filter { matrix.contains(it) } // Do not go where we already went, or to useless fields
        if (allowedCandidates.isEmpty()) {
            return null
        }

        return allowedCandidates.mapNotNull { next ->
            val pointCost = matrix.getOrDefault(next)
            if (pointCost == null) {
                null
            } else {
                val newForbidden =
                    forbidden + allowedCandidates // Since we now select one of the candidates, any other one is now a useless option since there's a more efficient path towards it...
                optimalPath(
                    // TODO could add some more forbiddens
                    matrix, path.add(next, pointCost), newForbidden, end
                )
            }
        }.minByOrNull { it.cost }
    }

    data class Path(val points: List<Point>, val cost: Int) {
        fun lastPoint(): Point {
            return points.last()
        }

        fun add(point: Point, pointCost: Int): Path {
            return Path(points + point, cost + pointCost)
        }

        companion object {
            fun empty(): Path {
                return Path(emptyList(), 0)
            }
        }
    }
}
