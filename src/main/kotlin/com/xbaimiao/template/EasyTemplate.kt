package com.xbaimiao.template

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.module.command.command
import org.bukkit.block.Block
import org.bukkit.entity.Player

@Suppress("unused")
class EasyTemplate : EasyPlugin() {


    override fun enable() {
        command("cnm") {
            exec {
                sender.sendMessage(player!!.getTargetBlock().type.name)
            }
        }.register()

    }

    private fun Player.getTargetBlock(): Block {
        return this.getTargetBlock(null, 100)
    }

    override fun disable() {

    }

}