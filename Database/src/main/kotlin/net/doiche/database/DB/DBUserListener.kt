package net.doiche.database.DB

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class DBUserListener: Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent){
        DBUserInit().init()
    }
}