package net.doiche.database.events

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class DataLoadEvent(player: Player): PlayerEvent(player), Cancellable {

    override fun getHandlers(): HandlerList {
        TODO("Not yet implemented")
    }

    override fun isCancelled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCancelled(cancel: Boolean) {
        TODO("Not yet implemented")
    }

}