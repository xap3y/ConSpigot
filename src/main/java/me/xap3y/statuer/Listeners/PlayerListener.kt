package me.xap3y.statuer.Listeners

import com.google.gson.JsonObject
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(private val wsServer: WSServer) : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        //Logger.info("Player joined")
        val playerInfo = WSResObj()
            .addProperty("name", e.player.name)
            .addProperty("display_name", e.player.displayName)
            .addProperty("op", e.player.isOp)
            .addProperty("hostname", e.player.address.hostName)
            .addProperty("host_address", e.player.address.address.hostAddress)
            .addProperty("health", e.player.health)
            .addProperty("food_level", e.player.foodLevel)
            .addProperty("world", e.player.location.world.name)
            .addProperty("x", String.format("%.3f", e.player.location.x).toDouble())
            .addProperty("y", String.format("%.3f", e.player.location.y).toDouble())
            .addProperty("z", String.format("%.3f", e.player.location.z).toDouble())
            .addProperty("yaw", e.player.location.yaw)
            .addProperty("pitch", e.player.location.pitch)
            .build()
        val obj = WSResObj()
            .addProperty("type", "event_playerJoin")
            .addArr("player_info", playerInfo)
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        //Logger.info("Player quit")
        val playerInfo = WSResObj()
            .addProperty("name", e.player.name)
            .addProperty("display_name", e.player.displayName)
            .build()
        val obj = WSResObj()
            .addProperty("type", "event_playerQuit")
            .addArr("player_info", playerInfo)
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val player = e.entity as Player
        val killer = player.killer
        val playerInfo = WSResObj()
            .addProperty("name", player.name)
            .addProperty("display_name", player.displayName)
            .addProperty("world", player.location.world.name)
            .addProperty("x", String.format("%.3f", player.location.x).toDouble())
            .addProperty("y", String.format("%.3f", player.location.y).toDouble())
            .addProperty("z", String.format("%.3f", player.location.z).toDouble())
            .addProperty("yaw", player.location.yaw)
            .addProperty("pitch", player.location.pitch)
            .build()

        var killerInfo: JsonObject? = null
        if (killer != null) {
            killerInfo = WSResObj()
                .addProperty("name", killer.name)
                .addProperty("display_name", killer.displayName)
                .addProperty("health", killer.player.health)
                .addProperty("food_level", killer.player.foodLevel)
                .addProperty("world", killer.location.world.name)
                .addProperty("x", String.format("%.3f", killer.location.x).toDouble())
                .addProperty("y", String.format("%.3f", killer.location.y).toDouble())
                .addProperty("z", String.format("%.3f", killer.location.z).toDouble())
                .addProperty("yaw", killer.location.yaw)
                .addProperty("pitch", killer.location.pitch)
                .build()
        }
        var obj = WSResObj()
            .addProperty("type", "event_playerDeath")
            .addArr("player_info", playerInfo)
        if (killerInfo != null) obj.addArr("killer_info", killerInfo)
        wsServer.broadcastMessage(obj.build())
    }
}