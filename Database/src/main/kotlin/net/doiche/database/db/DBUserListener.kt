package net.doiche.database.db

import net.doiche.database.events.DataLoadEvent
import net.doiche.hud.managers.HUDManager
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
        DBManager.playerDataRemove(e.player)
    }
    @EventHandler
    fun onDataLoaded(e:DataLoadEvent){
        HUDManager.runHUDTask(e.player)
    }
}