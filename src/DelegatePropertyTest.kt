import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Example {
    var p: String by Delegate()

}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name} in $thisRef.'")
    }
}

fun main(args: Array<String>) {
    val e = Example()
    println(e.p)

    e.p = "NEW"

    println(LazyTest().noticedLate)
    println(LazyTest().noticedLate)
    println(LazyTest().noticedLate)
    println(LazyTest().noticedLate)

    LazyTest1()
    LazyTest2()

    val user = User()
    user.name = "first"
    user.name = "second"

    val secondUser = SecondUser(mapOf(
            "name" to "John Doe",
            "age" to 25
    ))

    println(secondUser.name) // "John Doe" 출력
    println(secondUser.age) // 25 출력

    val y: Any? = null
//    val x: String? = y as String
}

class LazyTest {
    val noticedLate: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
        println("notice late!")
        "noticedLate"
    }
}

class LazyTest1 {
    init {
        println(LazyTest().noticedLate)
    }
}

class LazyTest2 {
    init {
        println(LazyTest().noticedLate)
    }
}


class User {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$prop, $old -> $new")
    }
}

class SecondUser(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

class MutableSecondUser(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int by map
}

interface Test {
    var x: String
}

class TestA : Test {
    override var x: String = "10"
}