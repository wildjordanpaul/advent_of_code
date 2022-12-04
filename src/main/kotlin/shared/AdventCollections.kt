package shared

import java.math.BigInteger
import java.util.*

inline fun <T> Iterable<T>.forEachPair(action: (Pair<T,T>) -> Unit) {
    this.forEach { x ->
        this.forEach{ y ->
            action(Pair(x,y))
        }
    }
}

val DEFAULT_DELIMITER = Regex("""[,\n]""")
fun String.splitLines(delimiter: Regex = DEFAULT_DELIMITER) = split(delimiter).map(String::trim).filter(String::isNotBlank)
fun String.splitLines(delimiter: String) = split(delimiter).map(String::trim).filter(String::isNotBlank)
fun String.splitInts(delimiter: String) = split(delimiter).map(String::trim).map(String::toInt)
fun String.splitInts(delimiter: Regex = DEFAULT_DELIMITER) = splitLines(delimiter).map(String::toInt)
fun String.splitLongs(delimiter: String) = split(delimiter).map(String::trim).filter(String::isNotBlank).map(String::toLong)
fun String.splitLongs(delimiter: Regex = DEFAULT_DELIMITER) = splitLines(delimiter).map(String::toLong)
fun String.splitDoubles(delimiter: String) = split(delimiter).map(String::trim).filter(String::isNotBlank).map(String::toDouble)
fun String.splitDoubles(delimiter: Regex = DEFAULT_DELIMITER) = splitLines(delimiter).map(String::toDouble)

fun Pair<Int, Int>.toRange() = first..second
fun String.toRange(delimiter: String = "-") = splitInTwo(delimiter).mapBoth(String::toInt).toRange()

fun Iterable<Point>.rangeX() = (minByOrNull(Point::x)?.x ?: 0)..(maxByOrNull(Point::x)?.x ?: 0)
fun Iterable<Point>.rangeY() = (minByOrNull(Point::y)?.y ?: 0)..(maxByOrNull(Point::y)?.y ?: 0)

fun <A> Pair<A, A>.reverse() = Pair<A, A>(second, first)

fun String.splitInTwo(delimiter: String = ","): Pair<String,String> {
    val pieces = split(delimiter, limit=2)
    return Pair(pieces.first(), pieces.last())
}

fun <R> String.splitInTwo(delimiter: String = ",", op: (String) -> R): Pair<R, R> {
    val pieces = split(delimiter, limit=2)
    return Pair(op(pieces.first()), op(pieces.last()))
}

fun String.splitAt(index: Int): Pair<String, String> {
    return Pair(substring(0 until index), substring(index until length))
}

fun <T, R> Pair<T, T>.mapBoth(op: (T) -> R): Pair<R, R> {
    return op(first) to op(second)
}

fun Collection<Int>.max() = maxOf { it }
fun Collection<Int>.min() = minOf { it }

fun <R> Collection<R>.ends() = Pair(first(), last())

fun Collection<BigInteger>.sum(): BigInteger {
    return fold(BigInteger.ZERO) { z, i -> i.add(z) }
}

fun <R> MutableMap<R, Int>.add(key: R, v: Int) {
    this[key] = getOrDefault(key, 0) + v
}

fun <R> MutableMap<R, Int>.subtract(key: R, v: Int) {
    this[key] = getOrDefault(key, 0) - v
}

fun <R> MutableMap<R, BigInteger>.add(key: R, v: BigInteger) {
    this[key] = getOrDefault(key, BigInteger.ZERO).add(v)
}

fun <R> MutableMap<R, BigInteger>.subtract(key: R, v: BigInteger) {
    this[key] = getOrDefault(key, BigInteger.ZERO).subtract(v)
}

fun <R> MutableMap<R, BigInteger>.inc(key: R) {
    this[key] = getOrDefault(key, BigInteger.ZERO).inc()
}

fun <R> MutableMap<R, BigInteger>.dec(key: R) {
    this[key] = getOrDefault(key, BigInteger.ZERO).dec()
}

fun Collection<Point>.shortestDistance(from: Point, to: Point, edge: (Point, Point) -> Int): Int? {
    val visited = mutableSetOf<Point>()
    val distanceMap = mutableMapOf(Point(0, 0) to 0)
    val queue = PriorityQueue<Vector>(compareBy { it.distance })
    queue.add(Vector(from, 0))

    while(queue.isNotEmpty()) {
        val current = queue.poll()
        current.point.directAdjacents.filter { contains(it) && !visited.contains(it) }.forEach { neighbor ->
            val neighborDistance = distanceMap[neighbor]
            val newDistance = current.distance + edge(current.point, neighbor)
            if(neighborDistance == null || newDistance < neighborDistance) {
                distanceMap[neighbor] = newDistance
                queue.add(Vector(neighbor, newDistance))
            }
        }
    }

    return distanceMap[to]
}

