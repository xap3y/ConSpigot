package me.xap3y.statuer.Config

import java.io.File

class SaveConfig {
    companion object{
        @JvmStatic
        fun SaveConfig(config: String, file: File): Boolean {
            return try {
                file.writeText(config)
                true
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            }
        }
    }
}