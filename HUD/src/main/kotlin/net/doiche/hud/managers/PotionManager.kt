package net.doiche.hud.managers

import org.bukkit.entity.Player
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

object PotionManager {

    /* 물약을 마시면 갈증 수치 6증가
     * 어색한 물약을 마시면 갈증 수치 0으로 변화 */
    fun thirstFilling (e:Player){

        val id = UserManager.getId(e.player!!.uniqueId) ?: return //음 !!
        val hud = HUDManager.getHUD(id) ?: return
        var thirst = hud.getThirst()

        val item = e.player!!.activeItem //음 !!
        val meta = (item.itemMeta as PotionMeta).basePotionData

        when (meta.type) {
            PotionType.WATER -> thirst += 6.0
            PotionType.AWKWARD -> thirst = 0.0
            else -> return
        }
        if (thirst > 20) thirst = 20.0

        hud.setThirst(thirst)
    }
}