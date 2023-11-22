package me.xap3y.statuer.Utils

import com.google.gson.JsonObject
import me.xap3y.statuer.Statuer
import org.bukkit.Bukkit

class Utils {
    companion object {
        fun executeCMD(command: String): Boolean {
            val sender = Bukkit.getServer().consoleSender
            val plugin: Statuer = Bukkit.getPluginManager().getPlugin("ConSpigot") as Statuer
            return Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.dispatchCommand(sender, command) }.get()
        }

        fun isOnline(player: String) : Boolean {
            return Bukkit.getServer().getPlayer(player).isOnline
        }

        fun errObj(errorMSG: String, type: String) : JsonObject {

            val errorObj = JsonObject()
            errorObj.addProperty("error", errorMSG)
            errorObj.addProperty("error_type", type)
            return errorObj
        }

        fun doneObj(doneMsg: String, type: String) : JsonObject {

            val doneObj = JsonObject()
            doneObj.addProperty("message", doneMsg)
            doneObj.addProperty("type", type)
            return doneObj
        }
    }
}