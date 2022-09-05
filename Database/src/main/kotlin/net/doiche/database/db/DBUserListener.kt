package net.doiche.database.db

import net.doiche.hud.HUD
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class DBUserListener: Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent){
        DBInitManager.playerDataInit(e.player)

    }
}