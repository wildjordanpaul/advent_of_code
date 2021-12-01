package shared

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

class AdventPusher {

    private val client by lazy {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .cookieJar(SantaCookieJar())
            .build()
    }

    fun push(day: Int, year: Int, level: Int, solution: Any?): Boolean {
        val body = FormBody.Builder()
            .add("level", level.toString())
            .add("answer", solution.toString())
            .build()

        val request = Request.Builder()
            .url("https://${Advent.DOMAIN}/${year}/day/${day}/answer")
            .post(body)
            .build()

        println("pushing answer to north pole for day: $day, level: $level, answer: $solution")
        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string().toString().trim()
            // println(responseBody)
            val incorrect = responseBody.contains("not the right answer")
            val status = if(incorrect) "NOT CORRECT" else "CORRECT"
            println("Solution was $status")
            return !incorrect
        }
    }
}