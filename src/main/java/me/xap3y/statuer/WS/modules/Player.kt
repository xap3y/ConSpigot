package me.xap3y.statuer.WS.modules

import com.google.gson.JsonObject
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.Utils.PlayerActions.Companion.Kick
import me.xap3y.statuer.Utils.ResObjs.Companion.getErrorObjRes
import me.xap3y.statuer.Utils.ResObjs.Companion.getSuccessObjRes
import org.bukkit.BanList
import org.bukkit.Bukkit
import java.util.*

class Player {
    companion object {
        fun kick(obj: Map<*, *>): JsonObject {
            val playerName = obj["player"].toString()
            val reason = obj["reason"].toString()

            Logger.info("REASON: $reason")

            if (playerName.isBlank()) return getErrorObjRes("Player name cannot be empty!")
            if (reason.isBlank()) return getErrorObjRes("Reason cannot be empty!")

            val response = Kick(playerName, reason)

            return if (response.get("error").asBoolean) {
                getErrorObjRes(response.get("cause").asString)
            } else {
                getSuccessObjRes(response.get("message").asString)
            }
        }

        fun permanentBan(obj: Map<*, *>, isIpBan: Boolean = false): JsonObject {
            //val bumper = StringUtils.repeat("\n", 35)
            val playerName = obj["player"].toString()
            val reason = obj["reason"].toString()

            if (playerName.isBlank()) return getErrorObjRes("Player name cannot be empty!")
            if (reason.isBlank()) return getErrorObjRes("Reason cannot be empty!")

            //Logger.info("BANNING: $playerName")
            //Logger.info("REASON: $reason")
            val type = if (isIpBan) BanList.Type.IP else BanList.Type.NAME
            return try {
                if (Bukkit.getBanList(type).isBanned(playerName)) {
                    Logger.info("2")
                    return getErrorObjRes("Player $playerName is already banned!")
                }

                Logger.info("1")
                Bukkit.getBanList(type).addBan(playerName, reason, null, null)
                if (Bukkit.getServer().getPlayer(playerName).isOnline) {
                    Kick(playerName, reason)
                }
                Logger.info("2")
                getSuccessObjRes("Player $playerName has been successfully banned with reason: $reason")
            } catch (e: Exception) {
                Logger.info("4")
                getErrorObjRes(e.message.toString())
            }
        }

        fun tempBan(obj: Map<*, *>, duration: Int, isIpBan: Boolean = false): JsonObject {
            if (duration < 1) return getErrorObjRes("Duration must be greater than 0")
            //val bumper = StringUtils.repeat("\n", 35)
            val date = Date(System.currentTimeMillis() + duration * 60 * 1000)

            val playerName = obj["player"].toString()
            val reason = obj["reason"].toString()

            if (playerName.isBlank()) return getErrorObjRes("Player name cannot be empty!")
            if (reason.isBlank()) return getErrorObjRes("Reason cannot be empty!")

            val type = if (isIpBan) BanList.Type.IP else BanList.Type.NAME
            return try {
                if (Bukkit.getBanList(type).isBanned(playerName)) {
                    return getErrorObjRes("Player $playerName is already banned!")
                }

                Bukkit.getBanList(type).addBan(playerName, reason, date, null)
                if (Bukkit.getServer().getPlayer(playerName).isOnline) {
                    Kick(playerName, reason)
                }
                getSuccessObjRes("Player $playerName has been successfully banned with reason: $reason for $duration minutes")
            } catch (e: Exception) {
                getErrorObjRes(e.message.toString())
            }
        }
    }
}