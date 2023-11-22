package me.xap3y.statuer.WS

import me.xap3y.statuer.Utils.Utils

class PlayerActions {
    companion object {
        fun MakeOP(player: String): String {
            return if (Utils.isOnline(player)) {
                Utils.executeCMD("op $player")
                "1"
            } else {
                "Player is not online"
            }
        }

        fun Kick(player: String, reason: String): String {
            return if (Utils.isOnline(player)) {
                Utils.executeCMD("kick $player $reason")
                "1"
            } else {
                "Player is not online"
            }
        }
    }
}