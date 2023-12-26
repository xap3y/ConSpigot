package me.xap3y.statuer.Utils

import com.google.gson.JsonObject
import me.xap3y.statuer.Config.ConfigStructure
import org.bukkit.Bukkit

class ServerInfo {
    companion object {
        fun getInfo(config: ConfigStructure): JsonObject {
            val name = Bukkit.getServer().name
            val version = Bukkit.getServer().version
            val bukkitVersion = Bukkit.getServer().bukkitVersion
            val port = Bukkit.getServer().port
            val ip = Bukkit.getServer().ip
            val maxPlayers = Bukkit.getServer().maxPlayers
            val currentPlayers = Bukkit.getServer().onlinePlayers.size
            val srvOgj = JsonObject()
            if(config.modules.serverName) srvOgj.addProperty("name", name)
            if(config.modules.serverVersion) srvOgj.addProperty("version", version)
            if(config.modules.bukkitVersion) srvOgj.addProperty("bukkitVersion", bukkitVersion)
            if(config.modules.serverIP) srvOgj.addProperty("ip", ip)
            if(config.modules.serverPort) srvOgj.addProperty("port", port)
            if(config.modules.maxPlayers) srvOgj.addProperty("maxPlayers", maxPlayers)
            if(config.modules.currentPlayers) srvOgj.addProperty("currentPlayers", currentPlayers)
            return srvOgj
        }
    }
}