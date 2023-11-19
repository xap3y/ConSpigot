package me.xap3y.conspigot.WS

import com.google.gson.JsonObject
import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory


class CPUMEMusage {
    companion object {
        private var osMxBean: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        private val cpu = this.osMxBean.processCpuLoad
        private val memTotal = Runtime.getRuntime().maxMemory() / (1024.0 * 1024.0)
        private val memFree = Runtime.getRuntime().freeMemory() / (1024.0 * 1024.0)
        private val memUsage = (memTotal - memFree)
        private val uptime = this.osMxBean.processCpuTime;
        private val loadAverage = this.osMxBean.systemLoadAverage
        private val name = this.osMxBean.name;

        fun getCpuUsage(): Double{
            return this.cpu * 100;
        }

        fun getMemObj(): JsonObject{
            val memObj = JsonObject()
            memObj.addProperty("total", memTotal)
            memObj.addProperty("free", String.format("%.2f", memFree).toDouble())
            memObj.addProperty("usage", String.format("%.2f", memUsage).toDouble())
            memObj.addProperty("uptimeCPU", uptime)
            memObj.addProperty("loadAverage", String.format("%.2f", loadAverage).toDouble())
            memObj.addProperty("name", name)
            return memObj
        }
    }
}