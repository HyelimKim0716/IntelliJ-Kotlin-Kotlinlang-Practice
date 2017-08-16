import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

/**
 *  Java Reflection : 객체를 통해 클래스의 정보를 분석해 내는 프로그램 기법
 *  실행중인 자바 프로그램 내부를 검사하고 내부 속성을 수정할 수 있음.
 *  객체의 메모리만 알고 객체의 형에 대해서 모른다고 가정할 때 Reflection 으로 객체의 메서드를 호출할 수 있다.
 *
 * Class c = Data.class;
 * // Class c = Class.forName("클래스이름");
 *
 * Method[] methods = c.getMethods();
 * Field[] fields = c.getFields();
 * Constructor[] constructors = c.getConstructors();
 * Class[] interfaces = c.getInterfaces();
 * Class superClass = c.getSuperclass();
 *
 * Kotlin Reflection : Kotlin 클래스에 대한 Reference 를 얻는 것
 *  - :: 이용
 *  - class, property 접근 시 :: 연산자 이용
 * */

interface Interface {
    var name: String
}

class DerivedClass : Interface {
    override var name: String = "Hello"
}


fun main(args: Array<String>) {
    val derived: DerivedClass = DerivedClass()
    derived.name = "Hyelim"

    val classReference = AnnotationTestJava::class
    println(classReference)

//    println(::isOdd)
    val numbers = listOf<Int>(1, 2, 3)
    println(numbers.filter(::isOdd))        // You can pass it as a value. :: 이용

    // Overloading function isOdd(x: String)
    val predicate: (String) -> Boolean = ::isOdd        // Refers to isOdd(x: String)
    println(predicate)

//    val extFun = String::toCharArray

    val oddLength = compose(::isOdd, ::length)
    val strings = listOf("a", "ab", "abc")

    println(strings.filter(oddLength))

    println(::propertyRef.get())
    ::propertyRef.set(2)
    println(propertyRef)

    val prop = A::p
    println(prop.get(A(1)))

    val str = listOf("a", "bc", "def")
    println(str.map(String::length))

    // 확장
     println(String::lastChar.get("ABCDEFG"))

    // Java Reflection
    println(A::p.javaGetter)
    println(A::p.javaField)

    // Constructor Reference
//    Foo1().function(::Foo1)

    val a1 = A(1)
    val a2 = A(1)
    val a3 = a1
    println(a1 === a2)
    println(a1 === a3)
}

fun isOdd(x: Int) = x % 2 != 0

fun isOdd(s: String) = s == "brilling" || s == "slity" || s == "tove"

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x))}
}

fun length(s: String) = s.length

var propertyRef = 1

class A(val p: Int)

// Extension
val String.lastChar: Char
get() = this[length - 1]

// Constructor Reference
class Foo1 {
    fun function(factory: () -> Foo1) {
        val x : Foo1 = factory()
    }
}
