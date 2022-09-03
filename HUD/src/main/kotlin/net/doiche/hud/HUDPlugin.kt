package net.doiche.hud

import org.bukkit.plugin.java.JavaPlugin

class HUDPlugin: JavaPlugin() {
    override fun onEnable() {
        server.logger.info("HUD Plugin.")
    }
}