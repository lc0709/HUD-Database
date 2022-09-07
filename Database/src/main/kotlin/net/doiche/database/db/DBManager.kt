package net.doiche.database.db

import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.hudMap
import net.doiche.hud.userMap
import kotlin.math.ceil

object DBManager {
    fun saveDB(){
        plugin.schedule(SynchronizationContext.ASYNC) {
            while(true){
                waitFor(12000L) // 10m
                saveHudData()
            }
        }
    }
    // "SELECT ? FROM ? WHERE ?=?"
    internal inline fun <reified T> get(queryString:String, get:String, returnDefault:Any? = null): T?{
        try {
            DBInitManager.connection.use {
                it.prepareStatement(queryString).use { state ->
                    val resultSet = state.resultSet
                    if (resultSet.next()) {
                        return resultSet.getObject(get, T::class.java)
                    } else {
                        warn("No such value at query.")
                        return returnDefault as T
                    }
                }
            }
        }catch(e: Exception){
            warn("Failed to getting value at query.")
            return null
        }
    }

    private fun saveHudData(){
        for(player in userMap) {
            val id = player.component2()
            val hud = hudMap[id] ?: return
            try {
                //player table init
                DBInitManager.connection.use {
                    it.prepareStatement("UPDATE hud SET thirst=?,stamina=?,temperature=? WHERE id=?").use { state ->
                        state.setInt(1, ceil(hud.thirst).toInt())
                        state.setInt(2, ceil(hud.stamina).toInt())
                        state.setInt(3, ceil(hud.temperature).toInt())
                        state.setInt(4, id)
                        state.execute()
                    }
                }
            } catch (e: Exception) {
                plugin.logger.warning("Failed to auto-saving player`s hud data.")
            }
        }
    }
}