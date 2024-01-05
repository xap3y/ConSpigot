package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent

class GamemodeListener(private val wsServer: WSServer, private val config: ConfigStructure) : Listener {
    @EventHandler
    fun onPlayerGameModeChangeEvent(e: PlayerGameModeChangeEvent) {
        if(!config.playerGamemodeChangeEvent.enabled) return
        var obj = WSResObj()
            .addProperty("type", "event_playerGameModeChange")
            .addProperty("player", e.player.name)
        if(config.playerGamemodeChangeEvent.showGamemode) obj.addProperty("game_mode", e.newGameMode.name)
        wsServer.broadcastMessage(obj.build())
    }
}