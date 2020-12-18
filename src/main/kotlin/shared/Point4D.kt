package shared

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) {

    val adjacents by lazy {
        listOf(x, x+1, x - 1).flatMap { x2 ->
            listOf(y, y+1, y-1).flatMap { y2 ->
                listOf(z, z+1, z-1).flatMap { z2 ->
                    listOf(w, w+1, w-1).mapNotNull { w2 ->
                        val point = Point4D(x2, y2, z2, w2)
                        if(point != this) point else null
                    }
                }
            }
        }
    }
}