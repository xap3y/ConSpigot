package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(private val wsServer: WSServer, private val config: ConfigStructure) : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        //Logger.info("Player joined")

        if (!config.playerJoinEvent.enabled) return
        val c = config.playerJoinEvent.playerInfoSettings
        var playerInfo = WSResObj()
            .addProperty("name", e.player.name)

        // Bruh this whole file is terrible but I don't know how to do it better
        if(c.displayName) playerInfo = playerInfo.addProperty("display_name", e.player.displayName)
        if(c.isOP) playerInfo.addProperty("op", e.player.isOp)
        if(c.hostname) playerInfo.addProperty("hostname", e.player.address.hostName)
        if(c.hostAddress) playerInfo.addProperty("host_address", e.player.address.address.hostAddress)
        if(c.health) playerInfo.addProperty("health", e.player.health)
        if(c.foodLevel) playerInfo.addProperty("food_level", e.player.foodLevel)
        if(c.world) playerInfo.addProperty("world", e.player.location.world.name)
        if(c.x) playerInfo.addProperty("x", String.format("%.3f", e.player.location.x).toDouble())
        if(c.y) playerInfo.addProperty("y", String.format("%.3f", e.player.location.y).toDouble())
        if(c.z) playerInfo.addProperty("z", String.format("%.3f", e.player.location.z).toDouble())
        if(c.yaw) playerInfo.addProperty("yaw", e.player.location.yaw)
        if(c.pitch) playerInfo.addProperty("pitch", e.player.location.pitch)
        val obj = WSResObj()
            .addProperty("type", "event_playerJoin")
            .addArr("player_info", playerInfo.build())
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        //Logger.info("Player quit")
        if(!config.playerQuitEvent.enabled) return
        var playerInfo = WSResObj()
            .addProperty("name", e.player.name)
        if(config.playerQuitEvent.showDisplayName) playerInfo.addProperty("display_name", e.player.displayName)

        val obj = WSResObj()
            .addProperty("type", "event_playerQuit")
            .addArr("player_info", playerInfo.build())
            .build()
        wsServer.broadcastMessage(obj)
    }

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        if(!config.playerDeathEvent.enabled) return
        val c = config.playerDeathEvent.playerInfoSettings
        val player = e.entity as Player
        val killer = player.killer
        var playerInfo = WSResObj()
            .addProperty("name", player.name)
            .addProperty("cause", player.lastDamageCause.cause.name)

        if(c.displayName) playerInfo = playerInfo.addProperty("display_name", player.displayName)
        if(c.world) playerInfo.addProperty("world", player.location.world.name)
        if(c.x) playerInfo.addProperty("x", String.format("%.3f", player.location.x).toDouble())
        if(c.y) playerInfo.addProperty("y", String.format("%.3f", player.location.y).toDouble())
        if(c.z) playerInfo.addProperty("z", String.format("%.3f", player.location.z).toDouble())
        if(c.yaw) playerInfo.addProperty("yaw", player.location.yaw)
        if(c.pitch) playerInfo.addProperty("pitch", player.location.pitch)

        var killerInfo: WSResObj? = null
        if (killer != null) {
            val c = config.playerDeathEvent.killerInfoSettings
            killerInfo = WSResObj()
                .addProperty("name", killer.name)

            if(c.displayName) killerInfo = killerInfo.addProperty("display_name", killer.displayName)
            if(c.health) killerInfo.addProperty("health", killer.health)
            if(c.foodLevel) killerInfo.addProperty("health", killer.foodLevel)
            if(c.world) killerInfo.addProperty("world", killer.location.world.name)
            if(c.x) killerInfo.addProperty("x", String.format("%.3f", killer.location.x).toDouble())
            if(c.y) killerInfo.addProperty("y", String.format("%.3f", killer.location.y).toDouble())
            if(c.z) killerInfo.addProperty("z", String.format("%.3f", killer.location.z).toDouble())
            if(c.yaw) killerInfo.addProperty("yaw", killer.location.yaw)
            if(c.pitch) killerInfo.addProperty("pitch", killer.location.pitch)
        }
        var obj = WSResObj()
            .addProperty("type", "event_playerDeath")
            .addArr("player_info", playerInfo.build())
        if (killerInfo != null) obj.addArr("killer_info", killerInfo.build())
        wsServer.broadcastMessage(obj.build())
    }
}