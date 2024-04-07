package com.xbaimiao.easylib

data class Library(
    val id: String,
    // 是否是云加载
    val cloud: Boolean,
    val apply: (Library.() -> Unit)? = null
) {

    val relocates = ArrayList<Relocate>()
    var repo: String? = null

    init {
        apply?.invoke(this)
    }

    fun relocate(name: String, relocate: String) {
        relocates.add(Relocate(name, relocate, cloud))
    }

    fun repo(repo: String) {
        this.repo = repo
    }

}
