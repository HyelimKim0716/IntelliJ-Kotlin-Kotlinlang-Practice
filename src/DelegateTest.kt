
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        println(x)
    }
}

class Derived(var b: Base) : Base by b

class Rectangle(val width:Int, val height:Int) {
    fun area() = width * height
}

class Window(val bounds:Rectangle, b:Base) : Base by b {
    fun area() = bounds.area()
}


val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) {
        result.add(t)
    }
    return result
}

fun main(args: Array<String>) {
    println("Start")
    val b = BaseImpl(10)
    Derived(b).print()      // 10

    val r = Rectangle(10, 30)
    val area = Window(r, b)
    println(area.area())

    println(lazyValue)
    println(lazyValue)

}