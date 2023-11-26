package me.xap3y.statuer.Utils

class Lag: Runnable {
    companion object {
        var TICK_COUNT = 0
        val TICKS = LongArray(600)
        var LAST_TICK = 0L

        fun getTPS(): Double {
            return getTPS(100)
        }

        fun getTPS(ticks: Int): Double {
            if (TICK_COUNT < ticks) {
                return 20.0
            }
            val target = (TICK_COUNT - 1 - ticks) % TICKS.size
            val elapsed = System.currentTimeMillis() - TICKS[target]
            return ticks / (elapsed / 1000.0)
        }

        fun getElapsed(tickID: Int): Long {
            if (TICK_COUNT - tickID >= TICKS.size) {
                // Handle this case if needed
            }

            val time = TICKS[(tickID % TICKS.size)]
            return System.currentTimeMillis() - time
        }
    }
    override fun run() {
        TICKS[TICK_COUNT % TICKS.size] = System.currentTimeMillis()
        TICK_COUNT += 1
    }
}