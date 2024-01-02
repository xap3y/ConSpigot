package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent

class PunishListener(private val wsServer: WSServer) : Listener {

    @EventHandler
    fun onPlayerKickEvent(e: PlayerKickEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_playerKick")
            .addProperty("player", e.player.name)
            .addProperty("reason", e.reason)
            .build()
        wsServer.broadcastMessage(obj)
    }
}