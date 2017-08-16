import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent

fun main(args: Array<String>) {

    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    print(adHoc.x + adHoc.y)

    val manager = DataProviderManager.registerDataProvider("provider" )
    val innerObject = DataProviderManager.InnerObject.innerProperty
    val test = MyClass.MyClassObject.myClassProprty

//    object AnotherObject {
//
//    }


    MyClass.create()
    MySecondClass.Companion.create()
    MySecondClass.create()
}

fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }
        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
// ...
}

object DataProviderManager {
    fun registerDataProvider(provider: String) {
        // ...
    }
    lateinit var allDataProviders: List<String>

    object InnerObject {
        var innerProperty = "Hello"
    }
}

class MyClass {
    object MyClassObject {
        val myClassProprty = "MyClassProperty"
    }

    companion object Factory {
        fun create(): MyClass = MyClass()

    }
}

val instance = MyClass.create()

class MySecondClass {
    companion object {
        fun create(): MySecondClass = MySecondClass()
    }
}
val x = MySecondClass.Companion
