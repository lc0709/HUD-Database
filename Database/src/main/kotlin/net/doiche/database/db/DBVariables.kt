package net.doiche.database.db

import net.doiche.database.DBMainPlugin

internal lateinit var plugin: DBMainPlugin

internal fun info(msg: String){
    plugin.logger.info(msg)
}
internal fun warn(msg: String){
    plugin.logger.warning(msg)
}
