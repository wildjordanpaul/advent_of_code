package shared

inline fun <T> Iterable<T>.forEachPair(action: (Pair<T,T>) -> Unit) {
    this.forEach { x ->
        this.forEach{ y ->
            action(Pair(x,y))
        }
    }
}

val DEFAULT_DELIMITER = Regex("""[,\n]""")
fun String.splitInts(delimiter: String) = split(delimiter).map(String::trim).map(String::toInt)
fun String.splitInts(delimiter: Regex = DEFAULT_DELIMITER) = split(delimiter).map(String::trim).map(String::toInt)
fun String.splitLongs(delimiter: String) = split(delimiter).map(String::trim).map(String::toLong)
fun String.splitLongs(delimiter: Regex = DEFAULT_DELIMITER) = split(delimiter).map(String::trim).map(String::toLong)
fun String.splitDoubles(delimiter: String) = split(delimiter).map(String::trim).map(String::toDouble)
fun String.splitDoubles(delimiter: Regex = DEFAULT_DELIMITER) = split(delimiter).map(String::trim).map(String::toDouble)

fun Iterable<Point>.rangeX() = (minByOrNull(Point::x)?.x ?: 0)..(maxByOrNull(Point::x)?.x ?: 0)
fun Iterable<Point>.rangeY() = (minByOrNull(Point::y)?.y ?: 0)..(maxByOrNull(Point::y)?.y ?: 0)

fun String.splitInTwo(delimiter: String = ","): Pair<String,String> {
    val pieces = split(delimiter, limit=2)
    return Pair(pieces.first(), pieces.last())
}

fun <R> String.splitInTwo(delimiter: String = ",", op: (String) -> R): Pair<R, R> {
    val pieces = split(delimiter, limit=2)
    return Pair(op(pieces.first()), op(pieces.last()))
}