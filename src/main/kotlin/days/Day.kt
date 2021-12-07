package days

import util.InputReader

abstract class Day(dayNumber: Int) {

    // lazy delegate ensures the property gets computed only on first access
    protected val inputList: List<String> by lazy { InputReader.getInputAsList(dayNumber) }
    protected val inputString: String by lazy { InputReader.getInputAsString(dayNumber) }

    open fun partOne(): Any {
        return runPartOne(inputList)
    }

    open fun runPartOne(lines: List<String>): Any {
        return 0
    }

    open fun partTwo(): Any {
        return runPartTwo(inputList)
    }

    open fun runPartTwo(lines: List<String>): Any {
        return 0
    }
}
