package chapter3

// 클래스 및 모든 함수 바깥 최상위 멤버
const val DEFAULT_PRIORITY = 3

class Task(
    val name: String,
    _priority: Int = DEFAULT_PRIORITY,  // 클래스 내에서 접근 간으
) {

    companion object {
        // 클래스 내에서 동반객체 선언 최상위
        const val MAX_PRIORITY = 5
        private const val MIN_PRIORITY = 1 // 클래스 내부에서만 사용하고자 하는 경우 private으로 막을 수 있음
    }

    var priority = validPriority(_priority)
        // Custom Setter를 통한 값의 할당
        set(value) {
            field = validPriority(value)
        }

    // MIN > this -> return min, MAX < this -> return max, else return this
    private fun validPriority(value: Int) = value.coerceIn(MIN_PRIORITY, MAX_PRIORITY)
}

fun main() {
    println(DEFAULT_PRIORITY)

    val task = Task("zayson", 2)
    task.priority = 10
    println("task1: ${task.priority}")

    val task2 = Task("zayson")
    println("task2: ${task2.priority}")
}