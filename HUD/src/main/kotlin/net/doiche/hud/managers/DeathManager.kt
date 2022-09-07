package net.doiche.hud.managers

import org.bukkit.entity.Player

object DeathManager {

    fun resetThirst(e:Player){
        val id = UserManager.getId(e.player!!.uniqueId) ?: return
        val hud = HUDManager.getHUD(id) ?: return
        hud.setThirst(20.0)
    }
}