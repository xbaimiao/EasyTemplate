package com.xbaimiao.template

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.util.ShortUUID
import com.xbaimiao.easylib.util.info

@Suppress("unused")
class EasyTemplate : EasyPlugin() {

    override fun enable() {
        info("测试")
        logger.info("${description.name} 插件启动成功 ${ShortUUID.randomShortUUID()}")
    }

}
