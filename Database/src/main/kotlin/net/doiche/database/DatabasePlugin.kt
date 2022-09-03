package net.doiche.database

import net.doiche.database.DB.DBTableInit
import net.doiche.database.DB.DBUserListener
import net.doiche.database.DB.plugin
import org.bukkit.plugin.java.JavaPlugin

class DatabasePlugin: JavaPlugin() {
    override fun onEnable() {
        plugin = this
        server.logger.info("DB Plugin.")

        server.pluginManager.registerEvents(DBUserListener(),this)
        DBTableInit().init()
        DBUpdateTask().saveDB()
    }
}