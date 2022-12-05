package shared

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

class AdventPuller(private val trimInput: Boolean = false) {

    private val cacheDir: File by lazy {
        val f = File("advent_input_cache")
        f.mkdirs()
        f
    }

    private val client by lazy {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .cache(Cache(cacheDir, 10 * 1024 * 1024))
            .cookieJar(SantaCookieJar())
            .build()
    }

    fun pull(day: Int, year: Int, fromCache: Boolean = true): String {
        val request = Request.Builder()
            .url("https://${Advent.DOMAIN}/${year}/day/${day}/input")

        if(fromCache) {
            println("pulling input from disk cache for day: $day")
            request.cacheControl(CacheControl.FORCE_CACHE)
        } else {
            println("pulling input from north pole for day: $day")
        }

        client.newCall(request.build()).execute().use { response ->
            return if(response.code == 504 && fromCache)
                pull(day, year, false)
            else {
                val body = response.body!!.string()
                if (trimInput) body.trim() else body
            }
        }
    }
}