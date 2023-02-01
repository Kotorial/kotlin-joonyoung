package chapter8

interface Dialable {
    fun dial(number: String): String
}

interface Snappable {
    fun takePicture(): String
}

class Phone : Dialable {
    override fun dial(number: String) = "Dialing ${number}"

    // public method 정의
    fun pushNumber(number: String, type: String = "KR") {
        matchCountry(type).also{ println("+$it $number") }
    }

    // private method 정의
    private fun matchCountry(type: String) =
        when (type) {
            "KR" -> "82"
            "US" -> "1"
            else -> "99"
        }
}

class Camera : Snappable {
    override fun takePicture() = "Taking Picture!"
}
