package shared

data class Point3D(val x: Int, val y: Int, val z: Int) {
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
}