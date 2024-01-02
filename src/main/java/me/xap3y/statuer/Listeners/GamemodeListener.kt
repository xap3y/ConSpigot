package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent

class GamemodeListener(private val wsServer: WSServer) : Listener {
    @EventHandler
    fun onPlayerGameModeChangeEvent(e: PlayerGameModeChangeEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_playerGameModeChange")
            .addProperty("player", e.player.name)
            .addProperty("game_mode", e.newGameMode.name)
            .build()
        wsServer.broadcastMessage(obj)
    }
}