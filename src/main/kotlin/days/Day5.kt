package days

import kotlin.math.abs

class Day5 : Day(5) {
    override fun partOne(): Any {
        return p1(inputList)
    }

    fun p1(ls: List<String>): Any {
        val lines = parseLines(ls).filter { it.isVerticalSameX() || it.isHorizontalSameY() }
        val maxPoint = findMaxPoint(lines)
        val ground = Ground(maxPoint)
        lines.forEach { line ->
            ground.add(line)
        }
        val result = ground.map { _: Day5Point, value: Int ->
            if (value > 1) {
                1
            } else {
                0
            }
        }.sum()
        println("Result $result")
//        ground.print()
        return result
    }

    fun findMaxPoint(lines: List<Line>) = lines.fold(Day5Point(0, 0)) { acc, line ->
        val maxX = maxOf(line.a.x, line.b.x, acc.x)
        val maxY = maxOf(line.a.y, line.b.y, acc.y)
        Day5Point(maxX + 1, maxY + 1)
    }

    fun parseLines(ls: List<String>) =
        ls.map { Line.from(it) }

    override fun partTwo(): Any {
        return p2(inputList)
    }

    fun p2(ls: List<String>): Any {
        val lines = parseLines(ls)
        val maxPoint = findMaxPoint(lines)
        val ground = Ground(maxPoint)
        lines.forEach { line ->
            ground.add(line)
        }
        val result = ground.map { _: Day5Point, value: Int ->
            if (value > 1) {
                1
            } else {
                0
            }
        }.sum()
        println("Result $result")
        ground.print()
        return result
    }
}

data class Ground(val maxPoint: Day5Point) {
    val ventLines = (0..maxPoint.y).map {
        (0..maxPoint.x).map {
            0
        }.toMutableList()
    }.toMutableList()

    fun add(line: Line) {
        line.forEachPoint { p: Day5Point ->
            ventLines[p.y][p.x] += 1
        }
    }

    fun <T> map(consumer: (Day5Point, Int) -> T): List<T> {
        return ventLines.flatMapIndexed { x, subList ->
            subList.mapIndexed { y, value ->
                consumer.invoke(Day5Point(x, y), value)
            }
        }
    }

    fun print() {
        ventLines.forEach { subList ->
            val line = subList.map {
                if (it == 0) {
                    "."
                } else {
                    it.toString()
                }
            }.joinToString("")
            println(line)
        }
    }
}

data class Day5Point(val x: Int, val y: Int) {
    companion object {
        fun from(s: String): Day5Point {
            val (x, y) = s.trim().split(",").map { it.trim().toInt() }
            return Day5Point(x, y)
        }
    }
}

data class Line(val a: Day5Point, val b: Day5Point) {
    // vertical is same x
    fun isVerticalSameX(): Boolean {
        return a.x == b.x
    }

    // horizontal is same y
    fun isHorizontalSameY(): Boolean {
        return a.y == b.y
    }

    fun forEachPoint(consumer: (Day5Point) -> Unit) {
        points().forEach {
            consumer.invoke(it)
        }
    }

    fun points(): List<Day5Point> {
        val minX = minOf(a.x, b.x)
        val maxX = maxOf(a.x, b.x)
        val minY = minOf(a.y, b.y)
        val maxY = maxOf(a.y, b.y)

        return if (maxX == minX) {
            (minY..maxY).map { y ->
                (Day5Point(maxX, y))
            }
        } else if (maxY == minY) {
            (minX..maxX).map { x ->
                (Day5Point(x, maxY))
            }
        } else {
            // Diagonal
            val xStep = if (a.x < b.x) {
                1
            } else {
                -1
            }
            val yStep = if (a.y < b.y) {
                1
            } else {
                -1
            }

            val times = abs(a.x - b.x)
            (0..times).map { i ->
                Day5Point(a.x + xStep * i, a.y + yStep * i)
            }
        }
    }

    companion object {
        fun from(s: String): Line {
            val (pointAString, pointBString) = s.split(Regex.fromLiteral("->"))
            return Line(Day5Point.from(pointAString), Day5Point.from(pointBString))
        }
    }
}
