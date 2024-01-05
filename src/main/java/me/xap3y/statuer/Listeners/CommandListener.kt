package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

class CommandListener(private val wsServer: WSServer) : Listener {

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_command")
            .addProperty("player", e.player.name)
            .addProperty("command", e.message)
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onServerCommand(e: ServerCommandEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_serverCommand")
            .addProperty("command", e.command)
            .build()
        wsServer.broadcastMessage(obj)
    }
}