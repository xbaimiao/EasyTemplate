package com.xbaimiao.easylib

data class Relocate(val pattern: String, val replacement: String, val cloud: Boolean) {

    constructor(pattern: String, replacement: String) : this(pattern, replacement, true)

}
