package net.doiche.database

import net.doiche.database.db.*
import net.doiche.database.db.info
import net.doiche.database.db.plugin
import org.bukkit.plugin.java.JavaPlugin

class DBMainPlugin: JavaPlugin() {
    override fun onEnable() {
        plugin = this
        info("DB Plugin.")

        server.pluginManager.registerEvents(DBUserListener(),this)
        DBInitManager.serverInit()
        DBManager.saveDB()
    }
}