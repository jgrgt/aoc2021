package days

class Day2 : Day(2) {
    override fun partOne(): Any {
        val commands = inputList.map { Command.from(it) }
        val position = commands.fold(Position(0, 0)) { position, command ->
            move(position, command)
        }
        return position.horizontal * position.depth
    }

    override fun partTwo(): Any {
        val commands = inputList.map { Command.from(it) }
        val position = commands.fold(Submarine(0, 0, 0)) { submarine, command ->
            moveWithAim(submarine, command)
        }
        return position.horizontal * position.depth
    }
}

fun move(p: Position, c: Command): Position {
    return when (c) {
        is Forward -> p.forward(c.steps)
        is Down -> p.down(c.steps)
        is Up -> p.up(c.steps)
    }
}

fun moveWithAim(s: Submarine, c: Command): Submarine {
    return when (c) {
        is Forward -> s.forward(c.steps)
        is Down -> s.down(c.steps)
        is Up -> s.up(c.steps)
    }
}

data class Position(val horizontal: Int, val depth: Int) {
    fun forward(steps: Int): Position {
        return copy(horizontal = horizontal + steps)
    }

    fun down(steps: Int): Position {
        return copy(depth = depth + steps)
    }

    fun up(steps: Int): Position {
        return copy(depth = depth - steps)
    }
}

data class Submarine(val horizontal: Int, val depth: Int, val aim: Int) {
    fun forward(steps: Int): Submarine {
        return copy(horizontal = horizontal + steps, depth = depth + aim * steps)
    }

    fun down(steps: Int): Submarine {
        return copy(aim = aim + steps)
    }

    fun up(steps: Int): Submarine {
        return copy(aim = aim - steps)
    }
}

sealed class Command {
    companion object {
        fun from(s: String): Command {
            val parts = s.split(" ")
            if (parts.size != 2) {
                error("Command should have 2 parts split by a space")
            }
            val (type, value) = parts
            return when (type) {
                "forward" -> Forward(value.toInt())
                "down" -> Down(value.toInt())
                "up" -> Up(value.toInt())
                else -> error("Unknown command ${type}")
            }
        }
    }
}

class Forward(val steps: Int) : Command()
class Up(val steps: Int) : Command()
class Down(val steps: Int) : Command()


