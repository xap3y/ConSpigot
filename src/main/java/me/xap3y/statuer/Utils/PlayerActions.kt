package me.xap3y.statuer.Utils

import com.google.gson.JsonObject
import me.xap3y.statuer.Utils.ResObjs.Companion.getErrorObjRes
import me.xap3y.statuer.Utils.ResObjs.Companion.getSuccessObjRes
import org.bukkit.Bukkit

@Suppress("FunctionName")
class PlayerActions {
    companion object {
        fun MakeOP(player: String): Boolean {
            return if (Utils.isOnline(player)) {
                //Utils.executeCMD("op $player")
                Bukkit.getServer().getPlayer(player).isOp = true
                true
            } else {
                false
            }
        }
        @JvmStatic
        fun Kick(player: String, reason: String): JsonObject {
            return if (Utils.isOnline(player)) {
                Utils.executeCMD("kick $player $reason")
                getSuccessObjRes("Player $player has been kicked with reason: $reason")
            } else {
                getErrorObjRes("Player $player is not online!")
            }
        }
    }
}