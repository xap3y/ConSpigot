package me.xap3y.conspigot.WS

import com.google.gson.JsonObject
import org.bukkit.Bukkit

class TPS {
    companion object {
        fun sendTPS(): JsonObject {
            val tps = getTPS();
            return tps;
        }

        private fun getTPS(): JsonObject {
            val tps = Bukkit.getServer().tps;
            val tpsObj = JsonObject()
            tpsObj.addProperty("1m", tps[0])
            tpsObj.addProperty("5m", tps[1])
            tpsObj.addProperty("15m", tps[2])
            return tpsObj
        }
    }
}