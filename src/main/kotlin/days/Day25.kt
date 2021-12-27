package days

import util.MutableMatrix
import util.Point

class Day25 : Day(25) {
    sealed class State
    object East : State() {
        override fun toString(): String {
            return ">"
        }
    }
    object South : State() {
        override fun toString(): String {
            return "v"
        }
    }
    object Empty : State() {
        override fun toString(): String {
            return "."
        }
    }

    override fun runPartOne(lines: List<String>): Any {
        var previous = MutableMatrix.fromSingleDigits(lines) { c ->
            when (c) {
                '.' -> Empty
                '>' -> East
                'v' -> South
                else -> error("Unknown character $c")
            }
        }

        var next = previous.move()
        var steps = 1
        while (previous != next) {
            steps += 1
            previous = next
            next = previous.move()
            check(steps < 100000) { "This is taking too long..."}
        }

        return steps
    }
}

fun MutableMatrix<Day25.State>.move(): MutableMatrix<Day25.State> {
    val result = moveEast()
    return result.moveSouth()
}

fun MutableMatrix<Day25.State>.moveEast(): MutableMatrix<Day25.State> {
    val result = clone()
    forEachPoint { p ->
        if (canMoveEast(p)) {
            result.doMove(p)
        }
    }
    return result
}

fun MutableMatrix<Day25.State>.moveSouth(): MutableMatrix<Day25.State> {
    val result = clone()
    forEachPoint { p ->
        if (canMoveSouth(p)) {
            result.doMove(p)
        }
    }
    return result
}

fun MutableMatrix<Day25.State>.doMove(p: Point) {
    val state = get(p)
    // we move away from the field, leaving an empty field behind
    set(p, Day25.Empty)
    when (state) {
        Day25.East -> {
            // check the east side
            val eastPoint = p.right()
            setWrapping(eastPoint, state)
        }
        Day25.South -> {
            // check the south side
            val southPoint = p.down()
            setWrapping(southPoint, state)
        }
        else -> {} // nothing
    }
}

fun MutableMatrix<Day25.State>.canMoveEast(p: Point): Boolean {
    return when (get(p)) {
        Day25.East -> {
            // check the east side
            val eastPoint = p.right()
            val eastValue = getWrapping(eastPoint)
            eastValue == Day25.Empty
        }
        else -> false
    }
}

fun MutableMatrix<Day25.State>.canMoveSouth(p: Point): Boolean {
    return when (get(p)) {
        Day25.South -> {
            // check the south side
            val southPoint = p.down()
            val southValue = getWrapping(southPoint)
            southValue == Day25.Empty
        }
        else -> false
    }
}
