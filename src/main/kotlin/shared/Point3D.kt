package shared

import java.util.NoSuchElementException

data class Point3D(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
    constructor(list: List<Int>) : this(list[0], list[1], list[2])

    constructor(points: String) : this(points.split(",").map(String::toInt))

    val adjacents by lazy {
        listOf(x, x+1, x - 1).flatMap { x2 ->
            listOf(y, y+1, y-1).flatMap { y2 ->
                listOf(z, z+1, z-1).mapNotNull { z2 ->
                    val point = Point3D(x2, y2, z2)
                    if(point != this) point else null
                }
            }
        }
    }

    val directAdjacents by lazy {
        listOf(
            Point3D(x+1, y, z),
            Point3D(x, y+1, z),
            Point3D(x, y, z+1),
            Point3D(x-1, y, z),
            Point3D(x, y-1, z),
            Point3D(x, y, z-1)
        )
    }

    fun add(x: Int = 0, y: Int = 0, z: Int = 0) : Point3D {
        return this.copy(x = this.x + x, y = this.y + y, z = this.z + z)
    }

    companion object {
        inline fun Iterable<Point3D>.boundaries(): Pair<Point3D, Point3D> {
            val iterator = iterator()
            if (!iterator.hasNext()) throw NoSuchElementException()
            val first = iterator.next()
            var minX = first.x
            var maxX = first.x
            var minY = first.y
            var maxY = first.y
            var minZ = first.z
            var maxZ = first.z
            while (iterator.hasNext()) {
                val v = iterator.next()
                if (minX > v.x) minX = v.x
                if (maxX < v.x) maxX = v.x
                if (minY > v.y) minY = v.y
                if (maxY < v.y) maxY = v.y
                if (minZ > v.z) minZ = v.z
                if (maxZ < v.z) maxZ = v.z
            }
            return Pair(Point3D(minX, minY, minZ), Point3D(maxX, maxY, maxZ))
        }
    }
}