package net.doiche.database.db

import net.doiche.database.DBMainPlugin

internal lateinit var plugin: DBMainPlugin
internal const val SELECT_ROW_FROM_PLAYER_WITH_UUID = "SELECT * FROM player WHERE uuid=?"
internal const val SELECT_ROW_FROM_HUD_WITH_ID = "SELECT * FROM hud WHERE id=?"
internal const val INSERT_INTO_PLAYER_AT_UUID_AND_NAME = "INSERT INTO player (uuid,name) VALUES (?,?)"
internal const val INSERT_INTO_HUD_AT_VALUES = "INSERT INTO hud (id,thirst,temperature,stamina) VALUES (?,?,?,?)"
internal fun info(msg: String){
    plugin.logger.info(msg)
}
internal fun warn(msg: String){
    plugin.logger.warning(msg)
}
