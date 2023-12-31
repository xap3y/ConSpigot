package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ChatListener(private val wsServer: WSServer) : Listener {
    @EventHandler
    fun onChat(event: org.bukkit.event.player.AsyncPlayerChatEvent) {
        val obj = WSResObj()
            .addProperty("type", "event_chat")
            .addProperty("player", event.player.name)
            .addProperty("message", event.message)
            .build()
        wsServer.broadcastMessage(obj)
    }
}