package me.xap3y.statuer.Utils

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class PlayerList {
    companion object {

        fun getOnlinePlayers(): JsonArray {
            val players = Bukkit.getOnlinePlayers()
            return getFromList(players.toList())
        }

        fun getFromList(onlinePlayers: List<Player>): JsonArray {
            val playerArrs = JsonArray()
            if (onlinePlayers.isEmpty()) return playerArrs

            onlinePlayers.forEach {
                val playerObj = JsonObject()
                playerObj.addProperty("name", it.name)
                playerObj.addProperty("uuid", it.uniqueId.toString())
                playerObj.addProperty("OP", it.isOp)
                playerObj.addProperty("displayName", it.displayName)
                playerObj.addProperty("health", it.health)
                playerObj.addProperty("food", it.foodLevel)
                playerObj.add("location", JsonObject().apply {
                    addProperty("x", it.location.x)
                    addProperty("y", it.location.y)
                    addProperty("z", it.location.z)
                    addProperty("pitch", it.location.pitch)
                    addProperty("yaw", it.location.yaw)
                    addProperty("world", it.location.world?.name)
                })
                playerArrs.add(playerObj)
            }
            return playerArrs
        }

        fun getFromOfflineList(offlinePlayers: List<OfflinePlayer>): JsonArray {
            val playerArrs = JsonArray()
            if (offlinePlayers.isEmpty()) return playerArrs
            offlinePlayers.forEach {
                val playerObj = JsonObject()
                playerObj.addProperty("name", it.name)
                playerObj.addProperty("uuid", it.uniqueId.toString())
                playerObj.addProperty("OP", it.isOp)
                playerObj.addProperty("banned", it.isBanned)
                playerObj.addProperty("whitelisted", it.isWhitelisted)
                playerArrs.add(playerObj)
            }
            return playerArrs
        }

        fun getOfflinePlayers(): JsonArray {
            val players = Bukkit.getOfflinePlayers()
            return getFromOfflineList(players.toList())
        }
    }
}