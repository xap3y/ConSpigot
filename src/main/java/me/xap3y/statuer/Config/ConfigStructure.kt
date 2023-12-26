package me.xap3y.statuer.Config

import kotlinx.serialization.Serializable
@Serializable
data class ConfigStructure(
    val serverName: String,
    val serverIP: String,
    val serverPort: Int,
    val socketAddress: String,
    val socketPort: Int,
    val passRequired: Boolean,
    val password: String,
    val logLevel: Int,
    val messages: Message,
    val modules: Modules
)

@Serializable
data class Message(
    val serverStopped: String,
    val startingThread: String,
    val StartingWebsocket: String,
    val StartedThread: String,
    val StartedWebsocket: String,
    val ConfigReload: String,
    val NoPermissions: String
)

@Serializable
data class Modules(
    val tps: Boolean,
    val memory: Boolean,
    val onlinePlayers: Boolean,
    val offlinePlayers: Boolean,
    val serverName: Boolean,
    val serverVersion: Boolean,
    val bukkitVersion: Boolean,
    val serverIP: Boolean,
    val serverPort: Boolean,
    val maxPlayers: Boolean,
    val currentPlayers: Boolean,
    val cpuLoad: Boolean,
    val uptime: Boolean
)