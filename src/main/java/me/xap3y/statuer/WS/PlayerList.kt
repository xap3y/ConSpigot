package me.xap3y.statuer.WS

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.Bukkit

class PlayerList {
    companion object {

        fun getOnlinePlayers(): JsonArray {
            val players = Bukkit.getOnlinePlayers()
            val PlayerArrs = JsonArray()
            if (players.isEmpty()) return PlayerArrs
            players.forEach {
                val playerObj = JsonObject()
                playerObj.addProperty("name", it.name)
                playerObj.addProperty("displayName", it.displayName)
                playerObj.addProperty("uuid", it.uniqueId.toString())
                playerObj.addProperty("health", it.health)
                playerObj.addProperty("food", it.foodLevel)
                playerObj.addProperty("OP", it.isOp)
                PlayerArrs.add(playerObj)
            }
            return PlayerArrs
        }

        fun getOfflinePlayers(): JsonArray {
            val players = Bukkit.getOfflinePlayers()
            val PlayerArrs = JsonArray()
            if (players.isEmpty()) return PlayerArrs
            players.forEach {
                val playerObj = JsonObject()
                playerObj.addProperty("name", it.name)
                playerObj.addProperty("uuid", it.uniqueId.toString())
                playerObj.addProperty("banned", it.isBanned)
                playerObj.addProperty("whitelisted", it.isWhitelisted)
                playerObj.addProperty("OP", it.isOp)
                PlayerArrs.add(playerObj)
            }
            return PlayerArrs
        }
    }
}