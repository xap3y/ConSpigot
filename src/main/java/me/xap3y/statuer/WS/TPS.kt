package me.xap3y.statuer.WS

import com.google.gson.JsonObject
import me.xap3y.statuer.Utils.Lag


class TPS {

    companion object {
        fun sendTPS(): JsonObject {
            return getTPS()
        }

        private fun getTPS(): JsonObject {
            /*
            SPARK METHOD

            val spark = SparkProvider.get()


            val tps = spark.tps()

            val tpsLast10Secs = tps!!.poll(TicksPerSecond.SECONDS_10)
            val tpsLast1Min = tps.poll(TicksPerSecond.MINUTES_1)
            val tpsLast5Mins = tps.poll(TicksPerSecond.MINUTES_5)
            val tpsLast15Mins = tps.poll(TicksPerSecond.MINUTES_15)

            */
            //val tps = Bukkit.getServer().tps

            val tps = arrayOf(Lag.getTPS(Lag.TICKS_PER_MINUTE), Lag.getTPS(Lag.TICKS_5_MINUTES), Lag.getTPS(Lag.TICKS_15_MINUTES))


            val tpsObj = JsonObject()
            tpsObj.addProperty("1m", String.format("%.2f", tps[0]).toDouble())
            tpsObj.addProperty("5m", String.format("%.2f", tps[0]).toDouble())
            tpsObj.addProperty("15m", String.format("%.2f", tps[0]).toDouble())
            return tpsObj
        }

    }
}