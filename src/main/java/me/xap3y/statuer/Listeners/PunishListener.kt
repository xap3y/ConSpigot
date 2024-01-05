package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.Listener

class PunishListener(private val wsServer: WSServer, private val config: ConfigStructure) : Listener {

    @EventHandler
    fun onPlayerKickEvent(e: PlayerKickEvent) {
        if (!config.playerKickEvent.enabled) return
        var obj = WSResObj()
            .addProperty("type", "event_playerKick")
            .addProperty("player", e.player.name)
        if (config.playerKickEvent.showReason) obj.addProperty("reason", e.reason)
        wsServer.broadcastMessage(obj.build())
    }
}