package me.xap3y.statuer.Config

import com.google.gson.Gson
import me.xap3y.statuer.Config.SaveConfig.Companion.SaveConfig
import java.io.File
import java.io.FileReader

class LoadConfig {
    companion object {
        private val gson = Gson()
        fun LoadConfig(file: File): ConfigStructure? {

            if (!file.exists()) {
                val defaultConfig = ConfigStructure(
                    "Lobby",
                    "localhost",
                    25565,
                    "0.0.0.0",
                    8080,
                    true,
                    "Password123",
                    1,
                    listOf(
                        Message(
                            "Server stopped",
                            "Starting websocker server...",
                            "Starting websocker server thread...",
                            "&a&lStarted websocker server thread.",
                            "&a&lStarted websocker server. %a",
                            "&aConfiguration reloaded",
                            "&cNo permissions!"
                        )
                    )
                )
                SaveConfig(defaultConfig, file)
            }

            return try {
                FileReader(file).use { reader ->
                    gson.fromJson(reader, ConfigStructure::class.java)
                }
            } catch (ex: Exception) {
                null
            }
        }
    }
}