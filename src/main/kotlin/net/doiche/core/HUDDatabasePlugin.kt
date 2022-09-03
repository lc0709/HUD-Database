package net.doiche.core

import org.bukkit.plugin.java.JavaPlugin

class HUDDatabasePlugin: JavaPlugin() {
    override fun onEnable() {
        server.logger.info("HUD && Database.")
    }
}