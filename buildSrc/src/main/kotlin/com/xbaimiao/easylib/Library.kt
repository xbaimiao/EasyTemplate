package com.xbaimiao.easylib

data class Library(
    val id: String,
    // 是否是云加载
    val cloud: Boolean,
    val relocates: Array<Relocate> = emptyArray(),
    val repo: String? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Library

        if (id != other.id) return false
        if (cloud != other.cloud) return false
        if (repo != other.repo) return false
        if (!relocates.contentEquals(other.relocates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + cloud.hashCode()
        result = 31 * result + (repo?.hashCode() ?: 0)
        result = 31 * result + relocates.contentHashCode()
        return result
    }

}
