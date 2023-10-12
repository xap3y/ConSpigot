package me.xap3y.conspigot.WS

import com.google.gson.JsonObject
import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory


class CPUusage {
    companion object {
        private var osMxBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        val cpu = this.osMxBean.systemCpuLoad;
        val memTotal = Runtime.getRuntime().totalMemory() / 1048576;
        val memFree = Runtime.getRuntime().freeMemory() / 1048576;
        val memUsage = (memTotal - memFree) / 1048576

        fun getCpuUsage(): Double{
            return this.cpu * 100;
        }

        fun getMemObj(): JsonObject{
            val memObj = JsonObject()
            memObj.addProperty("total", memTotal)
            memObj.addProperty("free", memFree)
            memObj.addProperty("usage", memUsage)
            return memObj
        }
    }
}