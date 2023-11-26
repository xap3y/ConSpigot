package me.xap3y.statuer.WS

import com.google.gson.JsonObject
import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory

class CPUMEMusage {
    companion object {
        private var runtime= Runtime.getRuntime()
        private var osMxBean: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        private val cpu = this.osMxBean.processCpuLoad
        private val memTotal = runtime.maxMemory() / (1024.0 * 1024.0)
        private var memFree: Double = 0.0
        private var memUsage: Double = 0.0
        private val uptime = this.osMxBean.processCpuTime;
        private val loadAverage = this.osMxBean.systemLoadAverage
        private val name = this.osMxBean.name;

        fun getCpuUsage(): Double{
            return this.cpu * 100;
        }

        fun getMemObj(): JsonObject {
            //runtime.gc()
            System.gc()
            memFree = runtime.freeMemory() / (1024.0 * 1024.0)
            memUsage = (memTotal - memFree)
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