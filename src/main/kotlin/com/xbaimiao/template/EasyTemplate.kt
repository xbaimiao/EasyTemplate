package com.xbaimiao.template

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.module.utils.Module

@Suppress("unused")
class EasyTemplate : EasyPlugin() {

    private val modules = ArrayList<Module<EasyTemplate>>()

    override fun enable() {
        modules.forEach { it.enable(this) }
    }

    override fun disable() {
        modules.forEach { it.disable(this) }
    }

}