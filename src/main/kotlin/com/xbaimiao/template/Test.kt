package com.xbaimiao.template

private val sortDamage get() = damageCache.map { Data(it.key, it.value) }.sortedBy { it.damage }.reversed()

private val damageCache = HashMap<String, Long>().apply {
    this["trmenu"] = 1000
    this["trmenu2"] = 10000000
    this["trmenu5"] = 1000000
    this["trmenud"] = 10000000
    this["trmenu3"] = 100000
}

open class Data(val name: String, val damage: Long) {

    fun run() {
        this::test.invoke()
    }

    open fun test() {
        println(1)
    }

}

class Data1 : Data("test", 1) {
    override fun test() {
        println(2)
    }
}

fun main() {
    val data: Data = Data1()
    data.run()
}