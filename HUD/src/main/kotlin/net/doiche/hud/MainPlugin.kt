package net.doiche.hud

import org.bukkit.plugin.java.JavaPlugin

class MainPlugin: JavaPlugin() {
    override fun onEnable() {
        plugin = this
        server.logger.info("HUD Plugin.")

        server.pluginManager.registerEvents(UserListener(),this)
    }
}