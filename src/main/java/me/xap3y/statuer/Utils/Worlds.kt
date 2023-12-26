package me.xap3y.statuer.Utils

import com.google.gson.JsonArray
import me.xap3y.statuer.WS.PlayerList.Companion.getFromList
import org.bukkit.Bukkit

class Worlds {
    companion object {
        fun getWorlds() : JsonArray {
            val worldsArr = JsonArray()
            Bukkit.getWorlds().forEach {
                worldsArr.add(WSResObj()
                    .addProperty("name", it.name)
                    .addProperty("loaded", it.isChunkLoaded(0, 0))
                    .addProperty("maxHeight", it.maxHeight)
                    .addProperty("pvp", it.pvp)
                    .addProperty("seed", it.seed)
                    .addProperty("time", it.time)
                    .addProperty("difficulty", it.difficulty.toString())
                    .addProperty("playersNum", it.players.size)
                    .addArr("players", getFromList(it.players.toList()))
                    .addProperty("entities", it.entities.size)
                    .addProperty("allowAnimals", it.allowAnimals)
                    .addProperty("allowMonsters", it.allowMonsters)
                    .addProperty("worldType", it.worldType.toString())
                .build()
                )
            }
            return worldsArr
        }

        /*fun getWorldsNames(loaded: Boolean = true) : List<String> {
            if (loaded) return Bukkit.getWorlds().filter { it != null && it.isChunkLoaded(0, 0) }.mapNotNull { it.name }
            return Bukkit.getWorlds().mapNotNull { it?.name }
        }*/
    }
}