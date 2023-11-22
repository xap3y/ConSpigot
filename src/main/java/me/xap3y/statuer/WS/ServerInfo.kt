package me.xap3y.statuer.WS

import com.google.gson.JsonObject
import org.bukkit.Bukkit

class ServerInfo {
    companion object {
        fun getInfo(): JsonObject {
            val name = Bukkit.getServer().name
            val version = Bukkit.getServer().version
            val bukkitVersion = Bukkit.getServer().bukkitVersion
            val port = Bukkit.getServer().port
            val ip = Bukkit.getServer().ip
            val maxPlayers = Bukkit.getServer().maxPlayers
            val currentPlayers = Bukkit.getServer().onlinePlayers.size
            val srvOgj = JsonObject()
            srvOgj.addProperty("name", name)
            srvOgj.addProperty("version", version)
            srvOgj.addProperty("bukkitVersion", bukkitVersion)
            srvOgj.addProperty("version", version)
            srvOgj.addProperty("ip", ip)
            srvOgj.addProperty("port", port)
            srvOgj.addProperty("maxPlayers", maxPlayers)
            srvOgj.addProperty("currentPlayers", currentPlayers)
            return srvOgj
        }
    }
}