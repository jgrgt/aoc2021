package days

import util.Point

class Day17 : Day(17) {
    override fun runPartOne(lines: List<String>): Any {
        //x=150..171, y=-129..-70
        val minX = 150
        val maxX = 171
        val minY = -129
        val maxY = -70

        val vector = findHighestLaunch(minX, maxX, minY, maxY)
        return findHighest(vector, minY)
    }

    override fun runPartTwo(lines: List<String>): Any {
        //x=150..171, y=-129..-70
        val minX = 150
        val maxX = 171
        val minY = -129
        val maxY = -70

        val vectors = findGoodLaunches(minX, maxX, minY, maxY)
        return vectors.size
    }

    private fun findHighest(vector: Point, minY: Int) = generate(vector)
        .takeWhile { probe -> probe.position.y >= minY }
        .map { it.position.y }
        .maxOrNull() ?: error("should have found a value")

    fun findHighestLaunch(minX: Int, maxX: Int, minY: Int, maxY: Int): Point {
        val highest = (1..maxX).mapNotNull { x ->
            val vectors = (1..1000).mapNotNull { y ->
                val vector = Point(x, y)
                val match = generate(vector)
                    .takeWhile { probe -> probe.position.y >= minY }
                    .filter { probe ->
                        val p = probe.position
                        (p.x in minX..maxX) && (p.y in minY..maxY)
                    }.firstOrNull()
                if (match != null) {
                    vector
                } else {
                    null
                }
            }
            val highestYVector = vectors.maxByOrNull { vector -> findHighest(vector, minY) }
            highestYVector
        }.maxByOrNull { vector ->
            findHighest(vector, minY)
        }
        return highest!!
    }

    fun findGoodLaunches(minX: Int, maxX: Int, minY: Int, maxY: Int): List<Point> {
        return (1..maxX).flatMap { x ->
            (-1000..1000).mapNotNull { y ->
                val vector = Point(x, y)
                val match = generate(vector)
                    .takeWhile { probe -> probe.position.y >= minY }
                    .filter { probe ->
                        val p = probe.position
                        (p.x in minX..maxX) && (p.y in minY..maxY)
                    }.firstOrNull()
                if (match != null) {
                    vector
                } else {
                    null
                }
            }
        }
    }

    fun generate(vector: Point): Sequence<Probe> {
        return generateSequence(Probe(Point(0, 0), vector)) { probe ->
            probe.next()
        }
    }

    data class Probe(val position: Point, val vector: Point) {
        fun next(): Probe {
            return Probe(position + vector, updatedVector())
        }

        fun updatedVector(): Point {
            val x = when {
                vector.x < 0 -> vector.x + 1
                vector.x > 0 -> vector.x - 1
                else -> 0
            }
            val y = vector.y - 1
            return Point(x, y)
        }
    }
}
