package net.doiche.hud

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import net.doiche.hud.tasks.HUDManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerToggleSprintEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType
import java.util.*

class UserListener: Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        HUDManager.runHUDTask(e.player)
    }



    /* 물약을 마시면 갈증 수치 6증가
     * 어색한 물약을 마시면 갈증 수치 0으로 변화 */
    @EventHandler
    fun onDrink(e: PlayerItemConsumeEvent) {

        val id = userMap[e.player.uniqueId]
        var thirst = hudMap[id]?.thirst //null 관련 수정필요

        if(thirst!=null){
            val item: ItemStack = e.player.activeItem
            val data = (Objects.requireNonNull(item.itemMeta) as PotionMeta).basePotionData

            when(data.type){
                PotionType.WATER -> thirst += 6.0
                PotionType.AWKWARD -> thirst = 0.0
                else -> return
            }; if(thirst>20) thirst=20.0

            hudMap[id]?.thirst = thirst
        }
    }

    @EventHandler
    fun onRun(e:PlayerToggleSprintEvent) {
        // 시간 조정, 나중에 구현
    }

    @EventHandler
    fun onJump(e:PlayerJumpEvent){
        // 시간 조정, 나중에 구현
    }

    @EventHandler
    fun onDeath(e:PlayerDeathEvent){

        val id = userMap[e.player.uniqueId]

        hudMap[id]?.thirst = 20.0
    }


}