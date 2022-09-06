package net.doiche.database.db

import net.doiche.hud.coroutine.SynchronizationContext
import net.doiche.hud.coroutine.schedule

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

    }
    private fun saveHudData(){

    }
}