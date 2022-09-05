package net.doiche.hud.tasks

import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.hudMap
import net.doiche.hud.plugin
import net.doiche.hud.userMap
import org.bukkit.entity.Player


object HUDManager {

    fun runHUDTask(player:Player){

        val id = userMap[player.uniqueId] ?: return
        val hud = hudMap[id] ?: return
        val thirst = hud.thirst

        plugin.schedule(SynchronizationContext.ASYNC){
            var count = 0
            while (player.isOnline) {

                if(count >= 200) {
                    val x = thirst - 1
                    hudMap[id]?.thirst = x
                    // thirst 구현하는 코드

                    count = 0
                }

                player.sendActionBar(hud.getComponent(player.isSwimming,player.remainingAir))
                count += 3
                waitFor(3)
            }
        }
    }
}