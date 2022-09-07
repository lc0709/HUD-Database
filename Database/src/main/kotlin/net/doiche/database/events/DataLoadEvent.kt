package net.doiche.database.events

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class DataLoadEvent(player: Player): PlayerEvent(player), Cancellable {
    private val handlerList = HandlerList()
    private var isCancelled = false

    override fun getHandlers() = handlerList

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

}