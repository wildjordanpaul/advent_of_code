package shared

inline fun <T> Iterable<T>.forEachPair(action: (Pair<T,T>) -> Unit) {
    this.forEach { x ->
        this.forEach{ y ->
            action(Pair(x,y))
        }
    }
}

fun String.splitInts(delimiter: String = ",") = split(delimiter).map(String::toInt)
fun String.splitDoubles(delimiter: String = ",") = split(delimiter).map(String::toDouble)

fun Iterable<Point>.rangeX() = (minBy(Point::x)?.x ?: 0)..(maxBy(Point::x)?.x ?: 0)
fun Iterable<Point>.rangeY() = (minBy(Point::y)?.y ?: 0)..(maxBy(Point::y)?.y ?: 0)

fun String.splitInTwo(delimiter: String = ","): Pair<String,String> {
    val pieces = split(delimiter, limit=2)
    return Pair(pieces.first(), pieces.last())
}