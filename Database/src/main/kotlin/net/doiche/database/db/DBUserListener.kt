package net.doiche.database.db

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class DBUserListener: Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent){
        DBInitManager.playerDataInit(e.player)
    }
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent){

    }
}