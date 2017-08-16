//@file:JvmName("Foo")

import java.lang.annotation.Inherited
import java.lang.reflect.Method
import kotlin.Deprecated
import kotlin.ReplaceWith
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions

/**
 * Annotation : 소스코드에 메타데이터 표현.
 *              Annotation 이 붙은 코드는 Annotation의 구현된 정보에 따라 연결되는 방향이 결정됨
 *              전체 소스코드에서 비즈니스 로직에는 영향을 주지 않지만 해당 타겟의 연결 방법이나 소스코드의 구조 변경 가능
 *              속성을 어떤 용도로 사용할 지, 클래스에게 어떤 역할을 하게할 지 결정하여 붙일 수 있음
 *
 * Java Built-in Annotation
 *  - @Override : 메소드가 오버라이드 됐는 지 검증
 *  - @Deprecated : 메소드를 사용하지 않도록 유도
 *  - @SuppressWarnings : 컴파일 경고 무시
 *  - @SafeVarags : 제너릭 같은 가변인자 매개변수 사용 시 경고 무시
 *  ...
 *
 *
 * Meta Annotation
 *  - @Target : Annotation 이 적용되는 요소 제한
 *  - @Retention : Annotation 이 얼마나 저장될 수 있는지 결정. Runtime 시 이용 가능한지, 컴파일 된 클래스 파일에서, 코드에서만 이용가능한 지
 *  - @Documented : 공용 API 문서의 한 부분이거나 API Documentation 에서 보여지는 class 또는 method 선언부에 포함되어야 하는 경우
 *  - @Repeatable : 같은 Annotation 을 하나의 요소에 반복적으로 선언 가능
 *
 *
 *
 *
 * */

/* Body is not allowed for annotaion class */
// 어노테이션 적용 위치 결정
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY,
        AnnotationTarget.FIELD,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.CONSTRUCTOR)
// 어노테이션 범위. 어떤 시점까지 어노테이션이 영향을 미치는 지 결정
@Retention(AnnotationRetention.RUNTIME)     // 컴파일 이후에도 JVM 에 의해 참조 가능
//@Retention(AnnotationRetention.CLASS)     // 컴파일러가 클래스를 참조
//@Retention(AnnotationRetention.SOURCE)    // Annotation 정보는 컴파일 이후 없어짐
@Repeatable                             // 반복적으로 어노테이션 선언
@MustBeDocumented                       // 문서에 어노테이션 정보 표현
@Inherited                              // 부모클래스에서 어노테이션을 상속받을 수 있음음
annotation class AnnotationNoParam

// 생성자에 AnnotationNoParam 을 붙일 경우 constructor 키워드 앞에 추가
@AnnotationNoParam class AnnotationTest @AnnotationNoParam constructor(private val name: String){
    //    @AnnotationNoParam fun print(@AnnotationNoParam foo: Int) {       // Error : Target 으로 지정한 종류만 @AnnotationNoParam 을 붙일 수 있음
    @AnnotationNoParam
    val greeting: String = "Hello"
    var age: Int = 1
        @AnnotationNoParam set(value) {
            if (value <= 0)
                field = 1
        }

    @AnnotationNoParam fun print(age: Int) {
        println("$greeting $name!! You are $age. It's time to get in shape.")

    }
}

/* Annotation can has constructor including parameters
* Allowed parameter types
*   - Types that correspond to Java primitive types (Int, Long etc.);
*   - String
*   - class(Foo::class)
*   - enums
*   - Other annotations
*   - Arrays of the types listed above
*
*   Annotation 을 다른 Annotation 의 파라메터로 사용하면 @ 문자를 이름 앞에 붙이지 않음
*
* */
annotation class AnnotationWithParam(val why: String)

@AnnotationWithParam("Just test") class Foo {

}

/* Other annotations as Kotlin Parameter */
annotation class ReplaceWith(val expression: String)

annotation class Deprecated(val message: String, val replaceWith: ReplaceWith = ReplaceWith("kotlin"))

//@Deprecated("This function is deprecated, use === instead", ReplaceWith("this === other")) fun print() {
//    println(AnnotationTest("Haley").print(27))
//}

/* Class as Kotlin Parameter */
annotation class Ann(val arg1: KClass<*>, val arg2: KClass<out Any>)
@Ann(String::class, Int::class)

/* Lamdas with annotations */
annotation class Suspendable
//val f = @Suspendable { Thread.sleep(10) }



//class Example2(@field:Ann(String::class, Int::class) val foo,       // 자바 필드
//              @get:Ann(String::class, Int::class) val bar,         // 자바 getter
//              @param:Ann(String::class, Int::class) val quux) {    // 자바 생성자 파라미터
//
//    /* Multiple Annotations */
//    @set:[AnnotationNoParam AnnotationWithParam("")]
//    var description: String = "Hello"
//}

/* Java annotation does not matter of order of the parameters, so use 이름 인자 구문 */
@AnnotationTestJava.JavaAnn(intValue = 10, stringValue = "abc") class C

/*value parameter is special case. You can set value parameter*/
@AnnotationTestJava.JavaAnnWithValue("abc") class D

/* If a value of an annotation constructor is array type, Kotlin can use vararg parameter */
@AnnotationTestJava.JavaAnnWithArrayValue("abc", "def", "ghi") class E

annotation class UserAnnotation(val number: Int, val text: String = "This is the first annotation")

@UserAnnotation(number = 0)
class UserAnnotationSample {

    @UserAnnotation(number = 1)
    fun annotationSample1() {}

    @UserAnnotation(number = 2, text = "second")
    fun annotationSample2() {}

    @UserAnnotation(3, "third")
    fun annotationSample3() {}

    fun checkAnnotations(userClass: Any) {
        val functions = userClass.javaClass.kotlin.declaredFunctions
        for (tmpFun in functions) {
            val annotations = tmpFun.annotations
            for (ann in annotations)
                println("${tmpFun.name}() : $ann")

        }

        val methods: Array<Method> = userClass.javaClass.declaredMethods        // 자바의 클래스에 해당하는 코틀린 클래스
        for (tmpMethod in methods) {
            val annotation = tmpMethod.getAnnotation(UserAnnotation::class.java)
            val number = annotation?.number
            val text = annotation?.text


            println("${tmpMethod.name}() : number = $number, text = $text")
        }

    }

}
fun main(args: Array<String>) {
    val sample = UserAnnotationSample()
    sample.checkAnnotations(UserAnnotationSample())
}