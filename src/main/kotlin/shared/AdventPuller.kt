package shared

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.lang.RuntimeException

class AdventPuller {

    companion object {
        const val ADVENT_DOMAIN = "adventofcode.com"
    }

    private class SantaCookieJar: CookieJar {
        private val adventCookie by lazy { System.getenv("ADVENT_COOKIE") }

        init {
            if(adventCookie.isEmpty()) {
                throw RuntimeException("INVALID COOKIE")
            }
        }

        override fun loadForRequest(url: HttpUrl) = listOf(
            Cookie.Builder()
                .domain(ADVENT_DOMAIN)
                .name("session")
                .value(adventCookie)
                .build()
        )

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}

    }

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

    fun pull(day: Int, fromCache: Boolean = true): String {
        val request = Request.Builder()
            .url("https://${ADVENT_DOMAIN}/2020/day/${day}/input")

        if(fromCache) {
            println("pulling input from disk cache for day: $day")
            request.cacheControl(CacheControl.FORCE_CACHE)
        } else {
            println("pulling input from north pole for day: $day")
        }

        client.newCall(request.build()).execute().use { response ->
            return if(response.code == 504 && fromCache)
                pull(day, false)
            else response.body!!.string().trim()
        }
    }
}