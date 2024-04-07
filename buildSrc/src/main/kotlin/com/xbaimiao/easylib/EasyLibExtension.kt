package com.xbaimiao.easylib

import groovy.transform.Canonical
import org.gradle.api.Action

/**
 * EasyLibExtension
 *
 * @author xbaimiao
 * @since 2023/8/29 14:43
 */
@Canonical
open class EasyLibExtension {

    var version: String = "3.7.5"

    var library = arrayOf<Library>()

    var relocate = arrayOf<Relocate>()

    var env = Env()

    fun env(action: Action<Env>) {
        action.execute(env)
    }

    fun getAllRelocate(): Array<Relocate> {
        val libraryRelocate = library.map { it.relocates }.toTypedArray().flatten().toMutableSet()
        libraryRelocate.addAll(relocate)
        return libraryRelocate.toTypedArray()
    }

}
