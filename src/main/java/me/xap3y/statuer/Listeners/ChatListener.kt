package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListener(private val wsServer: WSServer, private val config: ConfigStructure) : Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        if(!config.chatMessageEvent.enabled) return
        val obj = WSResObj()
            .addProperty("type", "event_chat")
            .addProperty("player", e.player.name)
            .addProperty("message", e.message)
            .build()
        wsServer.broadcastMessage(obj)
    }
}