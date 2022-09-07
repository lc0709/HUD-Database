package net.doiche.hud.managers

import net.doiche.hud.HUD
import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.plugin
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap


object HUDManager {

    private val hudMap = HashMap<Int, HUD>()

    fun inputHUD(id: Int,hud: HUD) {hudMap[id]=hud}

    fun getHudMap() = hudMap

    fun runHUDTask(player:Player){

        plugin.schedule(SynchronizationContext.ASYNC){
            var count = 0
            while (player.isOnline) {

                if(count >= 200) {

                    // thirst 구현하는 코드 작성해야 함


                    count = 0
                }

                //player.sendActionBar(hud.getComponent(player.isSwimming,player.remainingAir))
                count += 3
                waitFor(3)
            }
        }
    }
}