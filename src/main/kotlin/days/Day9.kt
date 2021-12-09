package days

import kotlin.streams.toList

class Day9 : Day(9) {
    override fun runPartOne(lines: List<String>): Any {
        // let's add a border of 9's
        val firstLine = lines[0]
        val width: Int = firstLine.length
        val border = listOf(9) + List(width) { 9 } + listOf(9)
        val heightMap: List<List<Int>> = listOf(border) + lines.map { line ->
            val heights = line.trim().chars().map { char ->
                char - '0'.code
            }.toList()
            listOf(9) + heights + listOf(9)
        } + listOf(border)
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
}
