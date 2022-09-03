package net.doiche.database.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.SQLException

object DBHikariInit {

    fun setSql(): Boolean {
        plugin.saveDefaultConfig()
        val config = plugin.config

        val serverName = config.getString("mysql.ip") ?: return false
        val port = config.getString("mysql.port") ?: return false
        val database = config.getString("mysql.database") ?: return false
        val user = config.getString("mysql.user") ?: return false
        val password = config.getString("mysql.password") ?: return false

        val hikari = HikariConfig()
        hikari.dataSourceClassName = "com.mysql.cj.jdbc.MysqlDataSource"
        hikari.addDataSourceProperty("serverName",serverName)
        hikari.addDataSourceProperty("port",port)
        hikari.addDataSourceProperty("databaseName",database)
        hikari.addDataSourceProperty("user",user)
        hikari.addDataSourceProperty("password",password)

        hikari.minimumIdle = 30
        hikari.maximumPoolSize = 30
        hikari.maxLifetime = 58000

        return try {
            source = HikariDataSource(hikari)
            true
        } catch (e: SQLException) {
            plugin.logger.warning("HikariDataSource 설정에 실패하였습니다.")
            false
        }
    }
}