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
val DEFAULT_LINE_DELIMITER = Regex("""[\n]""")
fun String.splitLines(delimiter: Regex = DEFAULT_LINE_DELIMITER) = split(delimiter).map(String::trim).filter(String::isNotBlank)
fun String.splitLines(delimiter: String) = split(delimiter).map(String::trim).filter(String::isNotBlank)
fun String.splitInts(delimiter: String) = split(delimiter).map(String::trim).map(String::toInt)
fun String.splitInts(delimiter: Regex = DEFAULT_DELIMITER) = splitLines(delimiter).map(String::toInt)
fun String.splitLongs(delimiter: String) = split(delimiter).map(String::trim).filter(String::isNotBlank).map(String::toLong)
fun String.splitLongs(delimiter: Regex = DEFAULT_DELIMITER) = splitLines(delimiter).map(String::toLong)
fun String.splitDoubles(delimiter: String) = split(delimiter).map(String::trim).filter(String::isNotBlank).map(String::toDouble)
fun String.splitDoubles(delimiter: Regex = DEFAULT_DELIMITER) = splitLines(delimiter).map(String::toDouble)

fun Pair<Int, Int>.toRange() = first..second
fun String.toRange(delimiter: String = "-") = splitInTwo(delimiter).mapBoth(String::toInt).toRange()

fun <R> String.toPointMap(op: (String) -> R) = splitLines().flatMapIndexed { y, line ->
    line.mapIndexed { x, c -> Point(x,y) to op(c.toString()) }
}.toMap()

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

fun String.extractInt(): Int = replace(Regex("[^0-9-]"), "").toInt()

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

fun <K, V> Map<K, V>.findKey(value: V): K = entries.find { it.value == value }!!.key

fun <R> Map<Point, R>.toGraph(op: (Pair<Point, R>, Pair<Point, R>) -> Boolean): Map<Point, List<Point>> {
    return mapValues { entry ->
        entry.key.directAdjacents.filter { point ->
            val nextVal = this[point]
            if(nextVal == null) false
            else op(entry.key to entry.value, point to nextVal)
        }
    }
}
data class Path(val point: Point, val list: List<Point>)

// Dijkstra's
fun Map<Point, List<Point>>.shortestPath(from: Point, dest: Point): List<Point>? {
    val visited = mutableSetOf<Point>()
    val pathCache = mutableMapOf<Point, List<Point>>()
    val queue = PriorityQueue<Path>(compareBy { it.list.size })
    queue.add(Path(from, listOf()))

    while(queue.isNotEmpty()) {
        val current = queue.poll()
        this[current.point]!!.filter { !visited.contains(it) }.forEach { neighbor ->
            val cachedPath = pathCache[neighbor]
            val newPath = listOf(neighbor) + current.list
            if(cachedPath == null || newPath.size < cachedPath.size) {
                pathCache[neighbor] = newPath
                queue.add(Path(neighbor, newPath))
            }
        }
    }

    return pathCache[dest]
}

public inline fun <T> Iterable<T>.flatFold(operation: (T, T) -> List<T>): List<T> {
    val iterator = this.iterator()
    if (!iterator.hasNext()) return emptyList()
    var last = iterator.next()
    if (!iterator.hasNext()) return emptyList()
    var accumulator = mutableListOf<T>()
    while (iterator.hasNext()) {
        val next = iterator.next()
        accumulator.addAll(operation(last, next))
        last = next
    }
    return accumulator
}

inline fun <T, R : Comparable<R>> Iterable<T>.minAndMaxOf(selector: (T) -> R): Pair<R, R> {
    val iterator = iterator()
    if (!iterator.hasNext()) throw NoSuchElementException()
    var minValue = selector(iterator.next())
    var maxValue = minValue
    while (iterator.hasNext()) {
        val v = selector(iterator.next())
        if (minValue > v) {
            minValue = v
        }
        if (maxValue < v) {
            maxValue = v
        }
    }
    return Pair(minValue, maxValue)
}

fun <T> LinkedHashSet<T>.pop(): T? = firstOrNull()?.also { remove(it) }

