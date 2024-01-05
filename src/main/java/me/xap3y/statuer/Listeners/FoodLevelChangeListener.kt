package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class FoodLevelChangeListener(private val wsServer: WSServer): Listener {
    @EventHandler
    fun onFoodLevelChangeEvent(e: FoodLevelChangeEvent) {
        val player = e.entity as Player
        val playerInfo = WSResObj()
            .addProperty("name", player.name)
            .addProperty("display_name", player.displayName)
            .addProperty("food_level", e.foodLevel)
            .build()
        val obj = WSResObj()
            .addProperty("type", "event_playerHungerChange")
            .addArr("player_info", playerInfo)
            .build()
        wsServer.broadcastMessage(obj)
    }
}