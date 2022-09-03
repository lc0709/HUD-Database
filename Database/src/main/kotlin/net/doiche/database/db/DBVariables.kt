package net.doiche.database.db

import com.zaxxer.hikari.HikariDataSource
import net.doiche.database.DBMainPlugin
import java.util.*
import kotlin.collections.HashMap

internal lateinit var plugin: DBMainPlugin
internal lateinit var source: HikariDataSource

internal val playerMap = HashMap<UUID, Int>()
internal val HudMap = HashMap<UUID, Int>()