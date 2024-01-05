package me.xap3y.statuer.Config

import com.google.gson.Gson
import me.xap3y.statuer.Config.SaveConfig.Companion.saveConfig
import java.io.File
import java.io.FileReader
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import me.xap3y.statuer.Utils.Logger

@Suppress("FunctionName")
class LoadConfig {
    companion object {
        private val gson = Gson()
        private val defaultConfig = ConfigStructure(
            "Lobby",
            "localhost",
            25565,
            "0.0.0.0",
            8080,
            true,
            "Password123",
            1,
            Message(
                "Server stopped",
                "Starting websocker server...",
                "Starting websocker server thread...",
                "&a&lStarted websocker server thread.",
                "&a&lStarted websocker server. %a",
                "&aConfiguration reloaded",
                "&cNo permissions!"
            ),
            Modules(
                tps = true,
                memory = true,
                onlinePlayers = true,
                offlinePlayers = true,
                serverName = true,
                serverVersion = true,
                bukkitVersion = true,
                serverIP = true,
                serverPort = true,
                maxPlayers = true,
                currentPlayers = true,
                cpuLoad = true,
                uptime = true
            ),
            PlayerJoinEvent(
                enabled = true,
                playerInfoSettings = PlayerJoinPlayer(
                    displayName = true,
                    isOP = true,
                    hostname = true,
                    hostAddress = true,
                    health = true,
                    foodLevel = true,
                    world = true,
                    x = true,
                    y = true,
                    z = true,
                    yaw = true,
                    pitch = true
                )
            ),
            PlayerQuitEvent(
                enabled = true,
                showDisplayName = true
            ),
            PlayerKickEvent(
                enabled = true,
                showReason = true
            ),
            PlayerGamemodeChangeEvent(
                enabled = true,
                showGamemode = true
            ),
            ChatMessageEvent(
                enabled = true,
                showMessage = true
            ),
            PlayerCommandEvent(
                enabled = true,
                showCommand = true
            ),
            ConsoleCommandEvent(
                enabled = true,
                showCommand = true
            ),
            FoodLevelChangeEvent(
                enabled = true,
                showDisplayName = true,
                showHunger = true
            ),
            PlayerDeathEvent(
                enabled = true,
                playerInfoSettings = PlayerDeathEventPlayer(
                    displayName = true,
                    world = true,
                    x = true,
                    y = true,
                    z = true,
                    yaw = true,
                    pitch = true
                ),
                killerInfoSettings = PlayerDeathEventKiller(
                    displayName = true,
                    health = true,
                    foodLevel = true,
                    world = true,
                    x = true,
                    y = true,
                    z = true,
                    yaw = true,
                    pitch = true
                )
            ),
        )
        @JvmStatic
        fun LoadConfig(file: File): ConfigStructure? {

            if (!file.exists()) {
                val json = Json { prettyPrint = true }
                val jsonString = json.encodeToString(defaultConfig)
                if (!saveConfig(jsonString, file)){
                    Logger.error("There was error creating config file!")
                }
            }

            return try {
                FileReader(file).use { reader ->
                    gson.fromJson(reader, ConfigStructure::class.java)
                }
            } catch (ex: Exception) {
                Logger.error("Config failed to load! Falling back to default config!")
                defaultConfig
            }
        }
    }
}