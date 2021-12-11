package util

data class MutableMatrix<T>(
    val items: MutableList<MutableList<T>>
) {
    companion object {
        fun <T> fromCommaLines(lines: List<String>, splitter: String = ",", creator: (String) -> T): MutableMatrix<T> {
            return MutableMatrix(
                lines.map { line ->
                    val items = line.split(splitter).map { creator.invoke(it.trim()) }
                    items.toMutableList()
                }.toMutableList()
            )
        }

        fun <T> fromSingleDigits(
            lines: List<String>,
            creator: (Char) -> T
        ): MutableMatrix<T> {
            return MutableMatrix(
                lines.map { line ->
                    val items = line.trim().toCharArray().map { creator.invoke(it) }
                    items.toMutableList()
                }.toMutableList()
            )
        }
    }

    fun set(p: Point, value: T) {
        items[p.x][p.y] = value
    }

    fun get(p: Point): T {
        return items[p.x][p.y]
    }

    fun getOrDefault(point: Point, default: T? = null): T? {
        return if (point.x < 0 || point.y < 0 || point.x >= items.size || point.y >= items[0].size) {
            default
        } else {
            return items[point.x][point.y]
        }
    }

    /**
     *   y y y
     * x
     * x
     * x
     */
    fun forEachPoint(consumer: (Point) -> Unit) {
        (0..items.size).forEach { x ->
            (0..items[0].size).forEach { y ->
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
