package days

import util.MutableMatrix
import util.Point
import kotlin.math.abs

class Day15 : Day(15) {
    override fun runPartOne(lines: List<String>): Any {
        val matrix = MutableMatrix.fromSingleDigits(lines) { c -> c.digitToInt() }
        val start = Point(0, 0)
        val optimal = aStar(matrix, start, matrix.max())
        return optimal.cost
    }

    override fun runPartTwo(lines: List<String>): Any {
        val matrix = MutableMatrix.fromSingleDigits(lines) { c -> c.digitToInt() }
        val megaMatrix = toMegaMatrix(matrix)
        val start = Point(0, 0)
        return aStar(megaMatrix, start, megaMatrix.max()).cost
    }

    fun toMegaMatrix(matrix: MutableMatrix<Int>): MutableMatrix<Int> {
        val additions = listOf(
            listOf(0, 1, 2, 3, 4),
            listOf(1, 2, 3, 4, 5),
            listOf(2, 3, 4, 5, 6),
            listOf(3, 4, 5, 6, 7),
            listOf(4, 5, 6, 7, 8),
        )
        val subMatrices = additions.map { row ->
            row.map { incr -> matrix.map { max9(it + incr) } }.map { it.items }
        }
        val longRowLists = subMatrices.flatMap { subMatrixRow ->
            toLongRows(subMatrixRow)
        }
        val megaMatrix = MutableMatrix(longRowLists.map {
            it.toMutableList()
        }.toMutableList())
        return megaMatrix
    }

    fun toLongRows(subMatrixRow: List<List<List<Int>>>): List<List<Int>> {
        return (0 until subMatrixRow[0].size).map {
            val r = subMatrixRow.map { sm -> sm[it].toList() }
            r.flatten()
        }
    }

    private fun max9(n: Int): Int {
        return if (n > 9) {
            n - 9
        } else {
            n
        }
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

    fun aStar(matrix: MutableMatrix<Int>, start: Point, end: Point): Path {
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
                matrix.print(hightlight = { p -> path.contains(p) })
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

    data class Path(val points: List<Point>, val cost: Int)
}
