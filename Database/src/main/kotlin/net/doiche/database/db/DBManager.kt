package net.doiche.database.db

import java.sql.SQLException

class DBManager {
    fun saveDB(){
        savePlayerData()
        saveHudData()
    }
    fun loadDB(){
        loadPlayerData()
        loadHudData()
    }
    // "SELECT ? FROM ? WHERE ?=?"
    fun get(queryString:String, get:String):String{
        try {
            connection.use {
                it.prepareStatement(queryString).use { state ->
                    val resultSet = state.resultSet
                    if (resultSet.next()) {
                        return resultSet.getString(get)
                    } else return "null"
                }
            }
        }catch(e: SQLException){
            plugin.logger.warning("Failed to getting value at query.")
            return "null"
        }
    }

    private fun savePlayerData(){

    }
    private fun saveHudData(){

    }
    private fun loadPlayerData(){

    }
    private fun loadHudData(){

    }
}