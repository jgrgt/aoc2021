package days

import kotlin.streams.toList

class Day9 : Day(9) {
    override fun runPartOne(lines: List<String>): Any {
        // let's add a border of 9's
        val firstLine = lines[0]
        val width: Int = firstLine.length
        val heightMap: List<List<Int>> = buildHeightMap(lines)
        val lowSpots = (1..width).flatMap { y ->
            (1..lines.size).mapNotNull { x ->
                val here = heightMap[x][y]
                val above = heightMap[x][y - 1]
                val below = heightMap[x][y + 1]
                val left = heightMap[x - 1][y]
                val right = heightMap[x + 1][y]
                if (here < above && here < below && here < left && here < right) {
                    here
                } else {
                    null
                }
            }
        }
        return lowSpots.sum() + lowSpots.size
    }

    override fun runPartTwo(lines: List<String>): Any {
        // let's add a border of 9's
        val firstLine = lines[0]
        val width: Int = firstLine.length
        val heightMap: List<List<Int>> = buildHeightMap(lines)
        val lowSpotCoordinates = (1..width).flatMap { y ->
            (1..lines.size).mapNotNull { x ->
                val here = heightMap[x][y]
                val above = heightMap[x][y - 1]
                val below = heightMap[x][y + 1]
                val left = heightMap[x - 1][y]
                val right = heightMap[x + 1][y]
                if (here < above && here < below && here < left && here < right) {
                    x to y
                } else {
                    null
                }
            }
        }
        val basinSizes = lowSpotCoordinates.map { (x, y) ->
            basinSize(heightMap, x, y)
        }.sorted().reversed()
        return basinSizes[0] * basinSizes[1] * basinSizes[2]
    }

    fun basinSize(heightMap: List<List<Int>>, x: Int, y: Int): Int {
        val start = Point(x, y)
        val covered = mutableSetOf(start)
        var newMembers = newMembers(heightMap, start)
        while (newMembers.isNotEmpty()) {
            covered.addAll(newMembers)
            newMembers = newMembers.map { newMembers(heightMap, it) }.fold(emptySet()) { acc, m -> acc.union(m) }
            newMembers = newMembers.subtract(covered)
            covered.addAll(newMembers)
        }
        return covered.size
    }

    fun newMembers(heightMap: List<List<Int>>, p: Point): Set<Point> {
        val candidates = listOf(p.up(), p.down(), p.left(), p.right())
        return candidates.filter { (x, y) ->
            heightMap[x][y] != 9
        }.toSet()
    }

    private fun buildHeightMap(lines: List<String>): List<List<Int>> {
        val width: Int = lines[0].length
        val border = listOf(9) + List(width) { 9 } + listOf(9)
        return listOf(border) + lines.map { line ->
            val heights = line.trim().chars().map { char ->
                char - '0'.code
            }.toList()
            listOf(9) + heights + listOf(9)
        } + listOf(border)
    }

    data class Point(val x: Int, val y: Int) {
        fun up(): Point {
            return Point(x, y - 1)
        }

        fun down(): Point {
            return Point(x, y + 1)
        }

        fun left(): Point {
            return Point(x - 1, y)
        }

        fun right(): Point {
            return Point(x + 1, y)
        }
    }
}

