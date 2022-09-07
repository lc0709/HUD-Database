package net.doiche.hud.managers

import net.doiche.hud.HUD
import java.util.UUID

object UserManager {

    private val userMap = HashMap<UUID,Int>()

    fun getId(uuid: UUID) = userMap[uuid]
    fun getUserMap() = userMap
    fun inputId(uuid: UUID,id: Int) { userMap[uuid] = id }

}