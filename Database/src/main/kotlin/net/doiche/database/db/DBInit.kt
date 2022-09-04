package net.doiche.database.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.entity.Player
import java.sql.SQLException

class DBInit {
    private val hikari = HikariConfig()
    private lateinit var source: HikariDataSource
    fun serverInit(){
        if (hikariInit()) createTable()
    }
    fun playerDataInit(player:Player){
        if (load(player)) firstInit(player)
    }

    private fun hikariInit(): Boolean{
        plugin.saveDefaultConfig()
        val config = plugin.config

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
        } catch (e: SQLException) {
            plugin.logger.warning("HikariDataSource Setting failed.")
            false
        }
    }
    private fun createTable(){
        try {
            connection = source.connection
            //connection init
            connection.use { connection ->
                connection.createStatement().use { state ->
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
        } catch (e: SQLException) {
            plugin.logger.warning("Table Creating Error.")
        }
    }


    private fun load(player:Player): Boolean{
        try {
            connection.use {
                it.prepareStatement("")
            }
        }catch(e:SQLException){
            plugin.logger.warning("Failed to loading player`s data!")
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
                }
            }
            //get id
            val id = DBManager().get("SELECT id FROM player", "id").toInt()
            connection.use {
                it.prepareStatement("INSERT INTO hud (id,thirst,temperature,stamina) VALUES (?,?,?,?)").use { state ->
                    state.setString(1, id.toString())
                    state.setString(2, hud[id]?.thirst.toString())
                    state.setString(3, hud[id]?.temperature.toString())
                    state.setString(4, hud[id]?.stamina.toString())
                }
            }
        } catch (e: SQLException) {
            plugin.logger.warning("Failed to initiating player data.")
        }
    }
}