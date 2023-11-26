package me.xap3y.statuer.Config

data class ConfigStructure(
    val serverName: String,
    val serverIP: String,
    val serverPort: Int,
    val SocketAddress: String,
    val SocketPort: Int,
    val passRequired: Boolean,
    val password: String,
    val logLevel: Int,
    val messages: List<Message>
)


data class Message(
    val serverStopped: String,
    val StartingThread: String,
    val StartingWebsocket: String,
    val StartedThread: String,
    val StartedWebsocket: String,
    val ConfigReload: String,
    val NoPermissions: String
)