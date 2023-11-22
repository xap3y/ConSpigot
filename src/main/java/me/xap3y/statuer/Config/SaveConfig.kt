package me.xap3y.statuer.Config

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

class SaveConfig {
    companion object{
        private val gson = Gson()
        fun SaveConfig(config: ConfigStructure, file: File) {
            try {
                FileWriter(file).use { writer ->
                    gson.toJson(config, writer)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}