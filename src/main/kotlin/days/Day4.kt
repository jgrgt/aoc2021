package days

class Day4 : Day(4) {
    override fun partOne(): Any {
        return p1(inputList)
    }

    fun p1(lines: List<String>): Any {
        val numbers = lines[0].split(",").map { it.toInt() }
        val boards = lines.drop(1).windowed(6, 6).map {
            Board.fromLines(it.drop(1))
        }.toMutableList()
        val bingo = Bingo(boards)

        val (number, winningBoard) = bingo.registerNumbers(numbers)
        return number * winningBoard.positiveNumber().sum()
    }

    override fun partTwo(): Any {
        return p2(inputList)
    }

    fun p2(lines: List<String>): Any {
        val numbers = lines[0].split(",").map { it.toInt() }
        val boards = lines.drop(1).windowed(6, 6).map {
            Board.fromLines(it.drop(1))
        }.toMutableList()
        val bingo = Bingo(boards)

        val (number, winningBoard) = bingo.registerNumbersUntilLastBoard(numbers)
        return number * winningBoard.positiveNumber().sum()
    }
}

data class Bingo(val boards: MutableList<Board>) {
    fun registerNumbers(numbers: List<Int>): Pair<Int, Board> {
        for (number in numbers) {
            registerNumber(number)
            val winningBoard = findWinningBoard()
            if (winningBoard != null) {
                return number to winningBoard
            }
        }
        error("no winner...")
    }

    // This should be some strategy
    fun registerNumbersUntilLastBoard(numbers: List<Int>): Pair<Int, Board> {
        for (number in numbers) {
            registerNumber(number)
            val winningBoards = findWinningBoards()
            if (winningBoards.isNotEmpty()) {
                if (boards.size == 1) {
                    return number to winningBoards[0]
                } else {
                    boards.removeAll(winningBoards)
                }
            }
        }
        boards.forEach { it.print() }
        error("no winner... ${boards.size} boards left")
    }

    private fun findWinningBoard(): Board? {
        return boards.find { it.isWinner() }
    }

    private fun findWinningBoards(): List<Board> {
        return boards.filter{ it.isWinner() }
    }

    private fun registerNumber(number: Int) {
        boards.forEach { it.registerNumber(number) }
    }
}

class Board(val matrix: MutableList<MutableList<Int>>) {
    fun registerNumber(number: Int) {
        matrix.forEach { row ->
            row.replaceAll { n ->
                if (n == number) {
                    -1
                } else {
                    n
                }
            }
        }
    }

    fun isWinner(): Boolean {
        return hasFullRow() || hasFullColumn()
    }

    private fun hasFullColumn(): Boolean {
        val rowSize = matrix[0].size
        val colSize = matrix.size
        return (0 until colSize).any { colIndex ->
            (0 until rowSize).all { rowIndex -> matrix[rowIndex][colIndex] < 0 }
        }
    }

    private fun hasFullRow(): Boolean {
        return matrix.any { row -> row.all { e -> e < 0 } }
    }

    fun positiveNumber(): List<Int> {
        return matrix.flatMap { row ->
            row.filter { it > 0 }
        }
    }

    fun print() {
        println("Board:")
        matrix.forEach { row ->
            println("%02d %02d %02d %02d %02d".format(row[0], row[1], row[2], row[3], row[4]))
        }
    }

    init {
        check(matrix.size == 5)
        check(matrix[0].size == 5)
    }

    companion object {
        fun fromLines(lines: List<String>): Board {
            val matrix = lines.map { line ->
                line.trim().split(Regex(" +")).map { it.trim().toInt() }.toMutableList()
            }.toMutableList()
            return Board(matrix)
        }
    }
}
