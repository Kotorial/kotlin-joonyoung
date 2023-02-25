package chapter11

import java.net.URL

class AstroRequest {
    companion object {
        private const val ASTRO_URL = "http://api.open-notify.org/astros.json"
    }

    // 인스턴스화된 클래스를 함수처럼 사용할 수 있음
    operator fun invoke(): String {
        return URL(ASTRO_URL).readText()
    }
}

fun main() {
    val request = AstroRequest()

    val result = request()
    println(result)
}