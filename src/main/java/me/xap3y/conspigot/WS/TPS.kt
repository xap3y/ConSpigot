package me.xap3y.conspigot.WS

import com.google.gson.JsonObject
import org.bukkit.Bukkit

class TPS {
    companion object {
        fun sendTPS(): JsonObject {
            return getTPS()
        }

        private fun getTPS(): JsonObject {
            val tps = Bukkit.getServer().tps
            val tpsObj = JsonObject()
            tpsObj.addProperty("1m", String.format("%.2f", tps[0]).toDouble())
            tpsObj.addProperty("5m", String.format("%.2f", tps[1]).toDouble())
            tpsObj.addProperty("15m", String.format("%.2f", tps[2]).toDouble())
            return tpsObj
        }
    }
}