package com.xbaimiao.template

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.util.ShortUUID

@Suppress("unused")
class EasyTemplate : EasyPlugin() {

    override fun enable() {
        logger.info("插件启动成功 ${ShortUUID.randomShortUUID()}")
    }

}
