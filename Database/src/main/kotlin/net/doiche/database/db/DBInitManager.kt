package net.doiche.database.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.SQLException

object DBInitManager {

    lateinit var source: HikariDataSource
    val connection:Connection get() = try{
        source.connection
    }catch(e: SQLException){
        throw e
    }

    //Enable
    fun serverInit(){
        if (hikariInit()) createTable()
    }
    //Listener
    fun playerDataInit(player:Player){
        if (!load(player)) firstInit(player)
    }

    private fun hikariInit(): Boolean{
        plugin.saveDefaultConfig()
        val config = plugin.config
        val hikari = HikariConfig()

        hikari.dataSourceClassName = "com.mysql.cj.jdbc.MysqlDataSource"
        hikari.addDataSourceProperty("serverName", config.getString("mysql.ip") ?: return false)
        hikari.addDataSourceProperty("port", config.getString("mysql.port") ?: return false)
        hikari.addDataSourceProperty("databaseName", config.getString("mysql.database") ?: return false)
        hikari.addDataSourceProperty("user", config.getString("mysql.user") ?: return false)
        hikari.addDataSourceProperty("password", config.getString("mysql.password") ?: return false)
        hikari.minimumIdle = 30
        hikari.maximumPoolSize = 30
        hikari.maxLifetime = 58000

        return try {
            source = HikariDataSource(hikari)
            true
        } catch (e: Exception) {
            plugin.logger.warning("HikariDataSource Setting failed.")
            false
        }
    }
    private fun createTable(){
        try {
            //connection init
            connection.use {
                it.createStatement().use { state ->
                    //player table
                    state.execute(
                        "CREATE TABLE IF NOT EXISTS player (" +
                                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                "uuid VARCHAR(36)," +
                                "name VARCHAR(16)" +
                                ")"
                    )
                    //hud table
                    state.execute(
                        "CREATE TABLE IF NOT EXISTS hud (" +
                                "id INT NOT NULL," +
                                "thirst INT NOT NULL," +
                                "temperature INT NOT NULL," +
                                "stamina INT NOT NULL" +
                                ")"
                    )
                }
            }
        } catch (e: Exception) {
            plugin.logger.warning("Table Creating Error.")
        }
    }


    private fun load(player:Player): Boolean{
        try {
            connection.use {
                var id = -1
                it.prepareStatement("SELECT * FROM player WHERE uuid="+player.uniqueId).use{state->
                    val set = state.resultSet
                    if (set.next()) id = set.getInt("id")
                    else return false
                }
                it.prepareStatement("SELECT * FROM hud WHERE id=$id").use{state->
                    val set = state.resultSet
                    if (set.next()) {
                        hud[id]?.thirst = set.getDouble("thirst")
                        hud[id]?.stamina = set.getDouble("stamina")
                        hud[id]?.temperature = set.getDouble("temperature")
                    }
                    else return false
                }
            }
        }catch(e: Exception){
            plugin.logger.warning("Failed to loading player`s data!")
            return false
        }
        return true
    }

    private fun firstInit(player:Player) {
        try {
            //player table init
            connection.use {
                it.prepareStatement("INSERT INTO player (uuid,name) VALUES (?,?)").use { state ->
                    state.setString(1, player.uniqueId.toString())
                    state.setString(2, player.name)
                    state.execute()
                }
            }
            //get id
            val id:Int = DBManager().get("SELECT id FROM player", "id") ?: return

            connection.use {
                it.prepareStatement("INSERT INTO hud (id,thirst,temperature,stamina) VALUES (?,?,?,?)").use { state ->
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
}