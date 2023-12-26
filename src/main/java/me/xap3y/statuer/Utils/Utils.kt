package me.xap3y.statuer.Utils

import com.google.gson.JsonObject
import me.xap3y.statuer.Statuer
import org.bukkit.Bukkit

class Utils {
    companion object {
        fun executeCMD(command: String): Boolean {
            return try {
                val sender = Bukkit.getServer().consoleSender
                val plugin: Statuer = Bukkit.getPluginManager().getPlugin("ConSpigot") as Statuer
                Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.dispatchCommand(sender, command) }.get()
            } catch (e: Exception) {
                false
            }

        }

        fun isOnline(player: String) : Boolean {
            return Bukkit.getServer().getPlayer(player).isOnline
        }
    }
}