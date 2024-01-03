package me.xap3y.statuer.Utils

import org.bukkit.Bukkit
import java.io.File

class Logger {
    companion object {
        private const val prefix = "[Statuser] ";
        fun info(msg: String){
            log(prefix + msg)
        }
        fun error(msg: String){
            log("$prefix(&c!&f) $msg")
        }
        private fun log(msg: String){
            Bukkit.getServer().consoleSender.sendMessage(Colors.colored(msg))
        }
        fun logFile(message: String, file: File) {
            try {
                if(!file.exists()) file.createNewFile()
                file.appendText(message + "\n")
            } catch (ex: Exception) {
                error("Error while logging to file!")
            }
        }
    }
}