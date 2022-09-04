<<<<<<< HEAD
package net.doiche.database.DB
=======
package net.doiche.database.db

import java.sql.SQLException
>>>>>>> origin/main

class DBTableInit {
    fun init(){
        createTable()
    }
    private fun createTable(){
<<<<<<< HEAD
        //player table
        //hud table
=======
        try {
            source.connection.use { connection ->
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
                                "id INT NOT NUL," +
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
>>>>>>> origin/main
    }
}
