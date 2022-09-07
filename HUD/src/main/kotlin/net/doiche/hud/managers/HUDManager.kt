package net.doiche.hud.managers

import net.doiche.hud.HUD
import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.plugin
import org.bukkit.entity.Player
import kotlin.collections.HashMap


object HUDManager {

    private val hudMap = HashMap<Int, HUD>()
    fun getHUD(id: Int) = hudMap[id]
    fun inputHUD(id: Int,hud: HUD) {hudMap[id]=hud}
    fun getHudMap() = hudMap

    fun runHUDTask(player:Player){

        val id = UserManager.getId(player.uniqueId)?: return

        val hud = getHUD(id)?: return

        var thirst = hud.getThirst()
        val stamina = hud.getStamina()
        val temperature = hud.getTemperature()


        plugin.schedule(SynchronizationContext.SYNC){ //HUD 관리 시스템
            var count = 0
            while (player.isOnline) {

                if(count >= 200) { //10초에 1씩 담

                    thirst -= 1
                    hud.setThirst(thirst)

                    count = 0
                }

                if(count >= 100 && UserManager.getExercising(id) == true){ //5초에 1씩 담

                    thirst -= 1
                    hud.setThirst(thirst)

                    UserManager.setExercising(id,false)
                    count = 0
                }

                if (count >= 40 && hud.getThirst() <= 0.0){ //갈증 ==0일 경우 2초마다 체력 2씩 담
                    player.damage(2.0)
                }
                //switchContext(SynchronizationContext.SYNC)
                player.sendActionBar(hud.getComponent(player.isSwimming, player.remainingAir))
                //switchContext(SynchronizationContext.ASYNC)
                count += 3
                waitFor(3)
            }
        }
    }
}
//굿