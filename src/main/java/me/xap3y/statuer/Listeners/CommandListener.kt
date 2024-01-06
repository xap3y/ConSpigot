package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

class CommandListener(private val wsServer: WSServer, private val config: ConfigStructure) : Listener {

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        if(!config.playerCommandEvent.enabled) return
        var obj = WSResObj()
            .addProperty("type", "event_command")
            .addProperty("player", e.player.name)
        if(config.playerCommandEvent.showCommand) {
            obj = obj.addProperty("command", e.message)
        }
        wsServer.broadcastMessage(obj.build())
    }

    @EventHandler
    fun onServerCommand(e: ServerCommandEvent) {
        if(!config.consoleCommandEvent.enabled) return
        var obj = WSResObj()
            .addProperty("type", "event_serverCommand")
        if(config.consoleCommandEvent.showCommand) obj = obj.addProperty("command", e.command)
        wsServer.broadcastMessage(obj.build())
    }
}