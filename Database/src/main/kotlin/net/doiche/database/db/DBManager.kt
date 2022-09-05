package net.doiche.database.db

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
    internal inline fun <reified T> get(queryString:String, get:String, returnDefault:Any? = null): T?{
        try {
            DBInitManager.connection.use {
                it.prepareStatement(queryString).use { state ->
                    val resultSet = state.resultSet
                    if (resultSet.next()) {
                        return resultSet.getObject(get, T::class.java)
                    } else {
                        plugin.logger.warning("No such value at query.")
                        return returnDefault as T
                    }
                }
            }
        }catch(e: Exception){
            plugin.logger.warning("Failed to getting value at query.")
            return null
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