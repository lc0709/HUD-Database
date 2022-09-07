package net.doiche.hud

import net.kyori.adventure.text.Component
import kotlin.math.ceil

class HUD(
    private var thirst: Double,
    private var temperature: Double,
    private var stamina: Double
) {
    fun getThirst() = thirst
    fun getTemperature() = temperature
    fun getStamina() = stamina

    fun setThirst(value: Double){
        thirst = value
    }
    fun setTemperature(value: Double){
        temperature = value
    }
    fun setStamina(value: Double){
        stamina = value
    }

    fun getComponent(isSwimming: Boolean, remainingAir: Int): Component {

        var display1 = ""

        var a = "\uE000\uF802" //풀
        var b = "\uE001\uF802" //반 + 띄어쓰기
        var c = "\uE002\uF802" //없
        val t = ceil(thirst).toInt()

        if (isSwimming || remainingAir < 300) {
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
        val x = display1.substring(0, display1.length - 1)



        val bar = "\uEA10"
        val tab1 = "\uF801"
        // -3
        val tab2 = "\uF80B" + "\uF809" + "\uF803"
        // -66-18-5
        val display = tab1 + bar + tab2 + x
        return Component.text(display)
    }

}