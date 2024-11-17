package com.xbaimiao.template

import com.xbaimiao.easylib.EasyPlugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.Lightable

@Suppress("unused")
class EasyTemplate : EasyPlugin() {

    override fun enable() {
        val location = Location(Bukkit.getWorld("world"), 129.0, 65.0, -270.0)
        location.block.type = Material.REDSTONE_LAMP

        val block = location.block

        val lightable = block.blockData as Lightable
        lightable.isLit = false
        block.setBlockData(lightable, true)

    }

}
