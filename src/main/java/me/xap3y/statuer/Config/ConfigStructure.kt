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
    val modules: Modules,
    val playerJoinEvent: PlayerJoinEvent,
    val playerQuitEvent: PlayerQuitEvent,
    val playerKickEvent: PlayerKickEvent,
    val playerGamemodeChangeEvent: PlayerGamemodeChangeEvent,
    val chatMessageEvent: ChatMessageEvent,
    val playerCommandEvent: PlayerCommandEvent,
    val consoleCommandEvent: ConsoleCommandEvent,
    val foodLevelChangeEvent: FoodLevelChangeEvent,
    val playerDeathEvent: PlayerDeathEvent
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


@Serializable
data class PlayerJoinEvent(
    val enabled: Boolean,
    val playerInfoSettings: PlayerJoinPlayer
)
@Serializable
data class PlayerJoinPlayer(
    val displayName: Boolean,
    val isOP: Boolean,
    val hostname: Boolean,
    val hostAddress: Boolean,
    val health: Boolean,
    val foodLevel: Boolean,
    val world: Boolean,
    val x: Boolean,
    val y: Boolean,
    val z: Boolean,
    val yaw: Boolean,
    val pitch: Boolean
)

@Serializable
data class PlayerQuitEvent(
    val enabled: Boolean,
    val showDisplayName: Boolean
)

@Serializable
data class PlayerKickEvent(
    val enabled: Boolean,
    val showReason: Boolean
)

@Serializable
data class PlayerGamemodeChangeEvent(
    val enabled: Boolean,
    val showGamemode: Boolean
)

@Serializable
data class ChatMessageEvent(
    val enabled: Boolean,
    val showMessage: Boolean
)

@Serializable
data class PlayerCommandEvent(
    val enabled: Boolean,
    val showCommand: Boolean
)

@Serializable
data class ConsoleCommandEvent(
    val enabled: Boolean,
    val showCommand: Boolean
)

@Serializable
data class FoodLevelChangeEvent(
    val enabled: Boolean,
    val showDisplayName: Boolean,
    val showHunger: Boolean
)

@Serializable
data class PlayerDeathEvent(
    val enabled: Boolean,
    val playerInfoSettings: PlayerDeathEventPlayer,
    val killerInfoSettings: PlayerDeathEventKiller,
)

@Serializable
data class PlayerDeathEventPlayer(
    val displayName: Boolean,
    val world: Boolean,
    val x: Boolean,
    val y: Boolean,
    val z: Boolean,
    val yaw: Boolean,
    val pitch: Boolean
)

@Serializable
data class PlayerDeathEventKiller(
    val displayName: Boolean,
    val health: Boolean,
    val foodLevel: Boolean,
    val world: Boolean,
    val x: Boolean,
    val y: Boolean,
    val z: Boolean,
    val yaw: Boolean,
    val pitch: Boolean
)