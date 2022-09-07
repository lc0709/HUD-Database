package net.doiche.hud

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import net.doiche.hud.managers.DeathManager
import net.doiche.hud.managers.PotionManager
import net.doiche.hud.managers.HUDManager
import net.doiche.hud.managers.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerToggleSprintEvent

class UserListener: Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {

    }

    @EventHandler
    fun onDrink(e: PlayerItemConsumeEvent) {
        PotionManager.thirstFilling(e.player)
    }

    @EventHandler
    fun onRun(e: PlayerToggleSprintEvent) {

        val id = UserManager.getId(e.player.uniqueId) ?: return
        UserManager.setExercising(id, true)
    }

    @EventHandler
    fun onJump(e: PlayerJumpEvent) {

        val id = UserManager.getId(e.player.uniqueId) ?: return
        UserManager.setExercising(id, true)
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        DeathManager.resetThirst(e.player)
    }
}