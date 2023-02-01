package chapter7

data class Person(
    val name: String,
    val age: Int = 1,
    val educations: List<Education>,
)

data class Education(
    val status: String,
    val type: String,
    val name: String,
)

fun main() {
    val personList = createPersons()
    personList.flatMap { it.educations }.filter { it.name == "A" && it.type == "Colleage" && it.status == "graduate" }
        .count().let {
            println("2023 졸업생 : ${it}")
            if (it > 2) "많은 졸업생"
            else "적당한 졸업생"
        }.also(::println)
    // A 대학을 졸업한 인원 구하기
    personList.flatMap { it.educations }.filter { it.name == "A" && it.type == "Colleage" && it.status == "graduate" }
        .count().also(::println)
}

private fun createPersons() = listOf(
    Person(
        "zayson", 29, listOf(
            Education("graduate", "Colleage", "A"),
            Education("graduate", "HighSchool", "B")
        )
    ),
    Person(
        "Maeng", 30, listOf(
            Education("graduate", "Colleage", "C"),
            Education("graduate", "HighSchool", "B")
        )
    ),
    Person(
        "Yun", 27, listOf(
            Education("graduate", "Colleage", "A"),
            Education("graduate", "HighSchool", "D")
        )
    )
)