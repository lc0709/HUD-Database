package net.doiche.database

import net.doiche.database.db.DBTableInit
import net.doiche.database.db.DBUpdateTask
import net.doiche.database.db.DBUserListener
import net.doiche.database.db.plugin
import org.bukkit.plugin.java.JavaPlugin

class DBMainPlugin: JavaPlugin() {
    override fun onEnable() {
        plugin = this
        server.logger.info("DB Plugin.")

        server.pluginManager.registerEvents(DBUserListener(),this)
        DBTableInit().init()
        DBUpdateTask().saveDB()
    }
}