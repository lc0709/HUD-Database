package net.doiche.hud

import java.util.UUID


internal lateinit var plugin: MainPlugin

val userMap = HashMap<UUID,Int>() //데이터베이스에서 로드받아야 함, 실패할 경우 오류 출력

val hudMap = HashMap<Int, HUD>() //데이터베이스에서 로드받아야 함, 실패할 경우 오류 출력