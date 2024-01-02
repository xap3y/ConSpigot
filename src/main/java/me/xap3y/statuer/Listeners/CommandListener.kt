package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

class CommandListener(private val wsServer: WSServer) : Listener {

    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_command")
            .addProperty("player", event.player.name)
            .addProperty("command", event.message)
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onServerCommand(event: ServerCommandEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_serverCommand")
            .addProperty("command", event.command)
            .build()
        wsServer.broadcastMessage(obj)
    }
}