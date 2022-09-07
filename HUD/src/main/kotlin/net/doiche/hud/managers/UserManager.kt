package net.doiche.hud.managers

import net.doiche.hud.HUD
import org.bukkit.entity.Player
import java.util.UUID

object UserManager {

    private val userMap = HashMap<UUID,Int>()

    private val userIsExercising = HashMap<Int,Boolean>() //데이터베이스 저장 X

    fun getId(uuid: UUID) = userMap[uuid]
    fun getUserMap() = userMap

    fun getExercising(id:Int) = userIsExercising[id]
    fun setExercising(id: Int, exercise: Boolean){
        userIsExercising[id] = exercise
    }

    fun inputId(uuid: UUID, id: Int) {
        userMap[uuid] = id
    }
}