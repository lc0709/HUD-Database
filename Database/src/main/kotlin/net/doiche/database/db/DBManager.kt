package net.doiche.database.db

import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.managers.HUDManager
import net.doiche.hud.managers.UserManager
import org.bukkit.entity.Player
import java.sql.PreparedStatement
import kotlin.math.ceil

object DBManager {
    fun updateDB(){
        plugin.schedule(SynchronizationContext.ASYNC) {
            info("[update] sequence running")
            while(true){
                waitFor(12000L) // 10m
                saveHudData()
                info("SAVING...")
            }
        }
    }
    fun playerDataRemove(player: Player){
        val id = UserManager.getUserMap().remove(player.uniqueId)
        HUDManager.getHudMap().remove(id)
    }
    // "SELECT ? FROM ? WHERE ?=?"
    internal inline fun <reified T> get(queryString:String, get:String, returnDefault:Any? = null, editState: (PreparedStatement) -> Unit): T?{
        try {
            DBInitManager.connection.use {
                it.prepareStatement(queryString).use { state ->
                    editState(state)
                    val resultSet = state.executeQuery()
                    if (resultSet.next()) {
                        return resultSet.getObject(get, T::class.java)
                    } else {
                        warn("No such value at query.")
                        return returnDefault as T
                    }
                }
            }
        }catch(e: Exception){
            e.printStackTrace()
            warn("Failed to getting value at query.")
            return null
        }
    }

   fun saveHudData(){
        for(player in UserManager.getUserMap()) {
            val id = player.component2()
            val hud = HUDManager.getHUD(id) ?: return
            try {
                //player table init
                DBInitManager.connection.use {
                    it.prepareStatement("UPDATE hud SET thirst=?,stamina=?,temperature=? WHERE id=?").use { state ->
                        state.setInt(1, ceil(hud.getThirst()).toInt())
                        state.setInt(2, ceil(hud.getStamina()).toInt())
                        state.setInt(3, ceil(hud.getTemperature()).toInt())
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