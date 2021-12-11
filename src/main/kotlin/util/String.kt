package util

fun String.multiLineInputLines() : List<String> {
    return this.trimIndent().lines()
}
