package me.xap3y.statuer.Utils

import com.google.gson.JsonObject
import me.xap3y.statuer.Utils.ResObjs.Companion.getErrorObjRes
import me.xap3y.statuer.Utils.ResObjs.Companion.getSuccessObjRes
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable


@Suppress("FunctionName")
class PlayerActions {
    companion object {
        fun MakeOP(player: String): JsonObject {
            val onlinePlayer = Bukkit.getServer().getPlayerExact(player) ?: return getErrorObjRes("Player $player is not online!")
            return if (onlinePlayer.isOp) {
                getErrorObjRes("Player $player is already OP!")
            } else {
                onlinePlayer.isOp = true
                getSuccessObjRes("Player $player has been made OP!")
            }
        }
        @JvmStatic
        fun Kick(player: String, reason: String): JsonObject {
            val onlinePlayer = Bukkit.getServer().getPlayerExact(player)
            return if (onlinePlayer != null) {

                object : BukkitRunnable() {
                    override fun run() {
                        onlinePlayer.kickPlayer(reason)
                    }
                }.runTask(Bukkit.getPluginManager().getPlugin("Statuer"))

                getSuccessObjRes("Player $player has been kicked with reason: $reason")
            } else {
                getErrorObjRes("Player $player is not online!")
            }
        }
    }
}