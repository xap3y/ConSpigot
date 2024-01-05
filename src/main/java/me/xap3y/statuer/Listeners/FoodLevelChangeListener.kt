package me.xap3y.statuer.Listeners

import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.WSResObj
import me.xap3y.statuer.WS.WSServer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class FoodLevelChangeListener(private val wsServer: WSServer, private val config: ConfigStructure): Listener {
    @EventHandler
    fun onFoodLevelChangeEvent(e: FoodLevelChangeEvent) {
        if(!config.foodLevelChangeEvent.enabled) return
        val player = e.entity as Player
        var playerInfo = WSResObj()
            .addProperty("name", player.name)

        if(config.foodLevelChangeEvent.showHunger) playerInfo.addProperty("food_level", e.foodLevel)
        if(config.foodLevelChangeEvent.showDisplayName) playerInfo.addProperty("display_name", player.displayName)
        val obj = WSResObj()
            .addProperty("type", "event_playerHungerChange")
            .addArr("player_info", playerInfo.build())
            .build()
        wsServer.broadcastMessage(obj)
    }
}