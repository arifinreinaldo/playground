package com.explore.playground.generic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import kotlin.reflect.KFunction1

class GenericActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)

        val list = listOf(Child("hoi", "hoi"), Child("hoi", "hoi"), Child("hoi", "hoi"))
        val list2 = listOf(Child2("hoi"), Child2("hai"))
        eats(list)
        eats(list2)

        val list3 = listOf(Child("brai", "hoi"), Child2("hoi"))

        val compare = Comparator { o1: Parent, o2: Parent ->
            o1.name.first().toInt() - o2.name.first().toInt()
        }
        list3.sortedWith(compare)
        list3.forEach {
            Log.d("GRAO", "onCreate: ${check(it, Parent::getType)}")
            Log.d("TAG", "tipe anak: ${it.getType()}")
        }

        val list4 = listOf(Anak1("hoi"), Anak1("hiboi"))
        list4.forEach {
            makan(it, it.makan())
        }
    }

    fun eats(list: List<Parent>) {
        list.forEach {
            it.eat()
        }
    }

    fun makan(ortu: Ortu, action: Unit) {
        ortu.run { action }
    }

    fun check(mammal: Parent, factCheck: KFunction1<Parent, String>): String {
        return factCheck(mammal)
    }

}

open class Parent(val name: String) {
    fun eat() {
        Log.d("TAG", "eat: Again")
    }
}

data class Child(val nameChild: String, val hands: String) : Parent(nameChild)
data class Child2(val nameChild: String) : Parent(nameChild)

sealed class Ortu(val name: String) {
    open fun makan() {}
}

data class Anak1(val nameChild: String) : Ortu(nameChild) {
    override fun makan() {
        Log.d("Makan", "makan cuuy ")
    }
}

fun Parent.getType(): String {
    return when (this) {
        is Child -> "Sulung"
        is Child2 -> "Bungsu"
        else -> "Tengah"
    }
}

fun Ortu.getTipe(): String {
    return when (this) {
        is Anak1 -> "sulung"
    }
}