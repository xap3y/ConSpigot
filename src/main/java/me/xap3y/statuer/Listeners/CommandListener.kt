package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CommandListener(private val wsServer: WSServer) : Listener {

    @EventHandler
    fun onCommand(event: org.bukkit.event.player.PlayerCommandPreprocessEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_command")
            .addProperty("player", event.player.name)
            .addProperty("command", event.message)
            .build()
        wsServer.broadcastMessage(obj)
    }
}