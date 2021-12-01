package shared

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.lang.RuntimeException

class SantaCookieJar: CookieJar {

    private val adventCookie by lazy { System.getenv("ADVENT_COOKIE") }

    init {
        if(adventCookie.isEmpty()) {
            throw RuntimeException("INVALID COOKIE")
        }
    }

    override fun loadForRequest(url: HttpUrl) = listOf(
        Cookie.Builder()
            .domain(Advent.DOMAIN)
            .name("session")
            .value(adventCookie)
            .build()
    )

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}
}