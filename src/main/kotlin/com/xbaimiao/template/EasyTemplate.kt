package com.xbaimiao.template

import com.cryptomorin.xseries.XMaterial
import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.util.ShortUUID

@Suppress("unused")
class EasyTemplate : EasyPlugin() {

    override fun enable() {
        println(XMaterial::class.java.name)
        logger.info("插件启动成功 ${ShortUUID.randomShortUUID()}")
    }

}
