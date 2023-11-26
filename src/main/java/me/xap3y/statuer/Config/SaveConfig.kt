package me.xap3y.statuer.Config

import java.io.File

class SaveConfig {
    companion object{
        fun SaveConfig(config: String, file: File) {
            try {
                file.writeText(config)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}