package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(private val wsServer: WSServer) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        //Logger.info("Player joined")
        val playerInfo = WSResObj()
            .addProperty("name", event.player.name)
            .addProperty("display_name", event.player.displayName)
            .addProperty("op", event.player.isOp)
            .addProperty("hostname", event.player.address.hostName)
            .addProperty("host_address", event.player.address.address.hostAddress)
            .addProperty("health", event.player.health)
            .addProperty("food_level", event.player.foodLevel)
            .addProperty("world", event.player.location.world.name)
            .addProperty("x", String.format("%.3f", event.player.location.x).toDouble())
            .addProperty("y", String.format("%.3f", event.player.location.y).toDouble())
            .addProperty("z", String.format("%.3f", event.player.location.z).toDouble())
            .addProperty("yaw", event.player.location.yaw)
            .addProperty("pitch", event.player.location.pitch)
            .build()
        val obj = WSResObj()
            .addProperty("type", "event_playerJoin")
            .addArr("player_info", playerInfo)
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        //Logger.info("Player quit")
        val playerInfo = WSResObj()
            .addProperty("name", event.player.name)
            .addProperty("display_name", event.player.displayName)
            .build()
        val obj = WSResObj()
            .addProperty("type", "event_playerQuit")
            .addArr("player_info", playerInfo)
            .build()
        wsServer.broadcastMessage(obj)
    }
}