package me.xap3y.statuer.Utils

import com.google.gson.JsonObject
import me.xap3y.statuer.Statuer
import me.xap3y.statuer.Utils.ResObjs.Companion.getErrorObjRes
import me.xap3y.statuer.Utils.ResObjs.Companion.getSuccessObjRes
import org.bukkit.Bukkit

class Utils {
    companion object {
        fun executeCMD(command: String): JsonObject {
            return try {
                val sender = Bukkit.getServer().consoleSender
                val plugin: Statuer = Bukkit.getPluginManager().getPlugin("Statuer") as Statuer
                Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.dispatchCommand(sender, command) }.get()
                getSuccessObjRes("Command executed successfully")
            } catch (e: Exception) {
                getErrorObjRes(e.message ?: "Unknown error")
            }

        }
    }
}