package net.doiche.hud.tasks

import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.hudMap
import net.doiche.hud.plugin
import net.doiche.hud.userMap
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.GameMode
import org.bukkit.entity.Player
import kotlin.math.ceil



object HUDManager {

    fun runHUDTask(e:Player){

        val id = userMap[e.uniqueId]
        val thirst = hudMap[id]!!.thirst

        plugin.schedule(SynchronizationContext.ASYNC){
            var count = 0
            while (e.isOnline) {

                if(count >= 200) {
                    val x = thirst - 1
                    hudMap[id]?.thirst = x
                    // thirst 구현하는 코드

                    count = 0
                }

                sendHUD(e)
                count += 3
                waitFor(3)
            }
        }
    }






    private fun sendHUD(e: Player) {

        val id = userMap[e.uniqueId]
        val thirst = hudMap[id]!!.thirst

        var display1: String? = ""
        var a = "\uE000\uF802" //풀

        var b = "\uE001\uF802" //반 + 띄어쓰기

        var c = "\uE002\uF802" //없

        if (e.gameMode == GameMode.SURVIVAL) {

            val t = ceil(thirst).toInt()

            if (e.isSwimming || e.remainingAir < 300) {
                a = "\uE003\uF802" //풀 물
                b = "\uE004\uF802" //반 물 + 띄어쓰기
                c = "\uE005\uF802" //없 물
            }

            if (t % 2 == 0) {
                val k = t / 2
                val x = 10 - k
                if (x != 0) {
                    for (i in x downTo 1) {
                        display1 += c
                    }
                }
                for (i in k downTo 1) {
                    display1 += a
                }
            } else {
                val k = t / 2
                val x = 9 - k
                for (i in x downTo 1) {
                    display1 += c
                }
                display1 += b
                for (i in k downTo 1) {
                    display1 += a
                }
            }
            val x = display1!!.substring(0, display1.length - 1)



            val bar = "\uEA10"
            val tab1 = "\uF801"
            // -3
            val tab2 = "\uF80B" + "\uF809" + "\uF803"
            // -66-18-5
            val display = tab1 + bar + tab2 + x
            e.sendActionBar(Component.text(display))
        }
    }
}