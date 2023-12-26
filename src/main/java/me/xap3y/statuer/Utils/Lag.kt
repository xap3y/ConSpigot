package me.xap3y.statuer.Utils

class Lag: Runnable {
    companion object {
        const val TICK_INTERVAL = 1L
        const val TICKS_PER_MINUTE = 60
        const val TICKS_5_MINUTES = 5 * TICKS_PER_MINUTE
        const val TICKS_15_MINUTES = 15 * TICKS_PER_MINUTE

        private var TICK_COUNT = 0
        private val TICKS = LongArray(TICKS_15_MINUTES)

        fun getTPS(ticks: Int): Double {
            if (TICK_COUNT < ticks) {
                return 20.0
            }
            val target = (TICK_COUNT - 1 - ticks) % TICKS.size
            val elapsed = System.nanoTime() - TICKS[target]
            return ticks / (elapsed / 1.0e9)
        }
    }

    override fun run() {
        TICKS[TICK_COUNT % TICKS.size] = System.nanoTime()
        TICK_COUNT += 1
    }
}