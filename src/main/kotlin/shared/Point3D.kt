package shared

data class Point3D(val x: Int, val y: Int, val z: Int) {
    val adjacents by lazy { listOf(
        Point3D(x+1, y, z),
        Point3D(x-1, y, z),
        Point3D(x+1, y+1, z),
        Point3D(x-1, y+1, z),
        Point3D(x+1, y-1, z),
        Point3D(x-1, y-1, z),
        Point3D(x, y+1, z),
        Point3D(x, y-1, z),
        Point3D(x+1, y, z + 1),
        Point3D(x-1, y, z + 1),
        Point3D(x+1, y+1, z + 1),
        Point3D(x-1, y+1, z + 1),
        Point3D(x+1, y-1, z + 1),
        Point3D(x-1, y-1, z + 1),
        Point3D(x, y+1, z + 1),
        Point3D(x, y-1, z + 1),
        Point3D(x, y, z + 1),
        Point3D(x+1, y, z - 1),
        Point3D(x-1, y, z - 1),
        Point3D(x+1, y+1, z - 1),
        Point3D(x-1, y+1, z - 1),
        Point3D(x+1, y-1, z - 1),
        Point3D(x-1, y-1, z - 1),
        Point3D(x, y+1, z - 1),
        Point3D(x, y-1, z - 1),
        Point3D(x, y, z - 1)
    ) }
}