package net.doiche.database.db

import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule
import net.doiche.hud.userMap

object DBManager {
    fun saveDB(){
        plugin.schedule(SynchronizationContext.ASYNC) {
            while(true){
                waitFor(12000L) // 10m
                savePlayerData()
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

    private fun savePlayerData(){
        for(player in userMap){

        }
        try {
            //player table init
            DBInitManager.connection.use {
                it.prepareStatement(INSERT_INTO_PLAYER_AT_UUID_AND_NAME).use { state ->
                    state.setString(1, player.uniqueId.toString())
                    state.setString(2, player.name)
                    state.execute()
                }
            }
            //get id
            val id:Int = DBManager.get(SELECT_ROW_FROM_PLAYER_WITH_UUID, "id") ?: return

            DBInitManager.connection.use {
                it.prepareStatement(INSERT_INTO_HUD_AT_VALUES).use { state ->
                    state.setInt(1, id)
                    state.setInt(2, 20)
                    state.setInt(3, 10)
                    state.setInt(4, 20)
                    state.execute()
                }
            }
        } catch (e: Exception) {
            plugin.logger.warning("Failed to initiating player data.")
        }
    }
    private fun saveHudData(){

    }
}