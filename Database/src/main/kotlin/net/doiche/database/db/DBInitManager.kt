package net.doiche.database.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.doiche.database.events.DataLoadEvent
import net.doiche.hud.HUD
import net.doiche.hud.managers.HUDManager
import net.doiche.hud.managers.UserManager
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
    fun unRegister(){
        source.close()
    }
    //Enable
    fun serverInit(){
        if (hikariInit()) createTable()
        plugin.server.pluginManager.callEvent(DataLoadEvent())
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
            info("[HIKARI] ON")
            true
        } catch (e: Exception) {
            warn("HikariDataSource Setting failed.")
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
            warn("Table Creating Error.")
        }
    }

    private fun load(player:Player): Boolean{
        var id: Int
        id = DBManager.get<Int>("SELECT id FROM player WHERE uuid=?","id"){
            it.setString(1, player.uniqueId.toString())
        } ?: -1
        try {
            if (id == -1) {
                info("Hi There! I`ll initiating this noob...")
                return false
            }
            connection.use {
                val uuid = player.uniqueId
                it.prepareStatement(SELECT_ROW_FROM_PLAYER_WITH_UUID).use{state->
                    state.setString(1, uuid.toString())
                    val set = state.executeQuery()
                    if (set.next()) {
                        id = set.getInt("id")
                        UserManager.inputId(uuid,id)
                    }
                    else {
                        warn("hud load err")
                        return true
                    }
                }
                it.prepareStatement(SELECT_ROW_FROM_HUD_WITH_ID).use{state->
                    state.setString(1, id.toString())
                    val set = state.executeQuery()
                    if (set.next()) {
                        HUDManager.inputHUD(id,HUD(
                            set.getDouble("thirst"),
                            set.getDouble("stamina"),
                            set.getDouble("temperature")
                        ))
                    }
                    else {
                        warn("hud load err.")
                        return true
                    }
                }
            }
            info("LOADED PLAYER")
        }catch(e: Exception){
            e.printStackTrace()
            warn("Failed to loading player`s data!")
            return true
        }
        return true
    }

    private fun firstInit(player:Player) {
        val uuid = player.uniqueId.toString()
        try {
            //player table init
            connection.use {
                it.prepareStatement(INSERT_INTO_PLAYER_AT_UUID_AND_NAME).use { state ->
                    state.setString(1, uuid)
                    state.setString(2, player.name)
                    state.execute()
                }
            }
            //get id
            val id:Int = DBManager.get("SELECT id FROM player WHERE uuid=?", "id"){
                it.setString(1,uuid)
            } ?: return

            connection.use {
                it.prepareStatement(INSERT_INTO_HUD_AT_VALUES).use { state ->
                    state.setInt(1, id)
                    state.setInt(2, 20)
                    state.setInt(3, 10)
                    state.setInt(4, 20)
                    state.execute()
                }
            }
            info("PLAYER INIT COMPLETED")
        } catch (e: Exception) {
            e.printStackTrace()
            warn("Failed to initiating player data.")
        }
    }
}