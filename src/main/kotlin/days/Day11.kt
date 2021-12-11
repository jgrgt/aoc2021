package days

class Day11 : Day(11) {
    override fun runPartOne(lines: List<String>): Any {
        val levels = parse(lines)
        val octopusses = Octopusses(levels)
        return (0..99).sumOf {
            octopusses.flash()
        }
    }

    override fun runPartTwo(lines: List<String>): Any {
        val levels = parse(lines)
        val octopusses = Octopusses(levels)
        var flashes = 0
        var steps = 0
        while(flashes != octopusses.size) {
            flashes = octopusses.flash()
            steps += 1
        }
        return steps
    }

    fun parse(lines: List<String>): List<List<Int>> {
        return lines.map { line ->
            line.map { c ->
                c.digitToInt()
            }
        }
    }

    class Octopusses(l: List<List<Int>>) {
        val levels = l.map { it.toMutableList() }.toMutableList()
        val size = levels.size * levels[0].size
        val flashed = -1

        fun flash(): Int {
            forEachPoint { point ->
                val level = getLevel(point)
                if (level != null) {
                    bumpLevel(point)
                }
            }
            var flashes = 0
            forEachPoint { p ->
                val level = getLevel(p)
                if (level == flashed) {
                    flashes += 1
                    setLevel(p, 0) // reset
                }
            }

            return flashes
        }

        private fun bumpLevel(p: Point): List<Point> {
            val level = getLevel(p) ?: return emptyList()

            return if (level == flashed) {
                // Do nothing, already flashed
                emptyList<Point>()
            } else if (level < 9) {
                setLevel(p, level + 1)
                emptyList<Point>()
            } else if (level == 9) {
                setLevel(p, flashed)
                p.around().flatMap {
                    bumpLevel(it)
                }
            } else {
                error("Invalid point value")
            }
        }

        private fun setLevel(p: Point, level: Int) {
            levels[p.x][p.y] = level
        }

        private fun getLevel(point: Point): Int? {
            return if (point.x < 0 || point.y < 0 || point.x >= levels.size || point.y >= levels[0].size) {
                null
            } else {
                levels[point.x][point.y]
            }
        }

        /**
         *  yyyyyyyyyyyyyyyyyyyyy
         * x
         * x
         * x
         * x
         * x
         * x
         * x
         */
        private fun forEachPoint(consumer: (Point) -> Unit) {
            levels.forEachIndexed { x, levelRow ->
                levelRow.forEachIndexed { y, _ ->
                    consumer.invoke(Point(x, y))
                }
            }
        }

    }

    data class Point(val x: Int, val y: Int) {
        fun around(): List<Point> {
            return listOf(
                Point(x - 1, y + 1),
                Point(x - 1, y),
                Point(x - 1, y - 1),
                Point(x, y + 1),
                Point(x, y - 1),
                Point(x + 1, y + 1),
                Point(x + 1, y),
                Point(x + 1, y - 1),
            )
        }
    }
}
