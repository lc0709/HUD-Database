package net.doiche.database

import net.doiche.database.db.DBInit
import net.doiche.database.db.DBManager
import net.doiche.database.db.DBUserListener
import net.doiche.database.db.plugin
import org.bukkit.plugin.java.JavaPlugin

class DBMainPlugin: JavaPlugin() {
    override fun onEnable() {
        plugin = this
        server.logger.info("DB Plugin.")

        server.pluginManager.registerEvents(DBUserListener(),this)
        DBInit().serverInit()
        DBManager().saveDB()
    }
}