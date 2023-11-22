package me.xap3y.statuer.Utils

import org.bukkit.Bukkit

class Logger {
    companion object {
        private const val prefix = "[Statuser] ";
        fun info(msg: String){
            Bukkit.getServer().consoleSender.sendMessage(Colors.colored(prefix + msg))
        }
        fun error(msg: String){
            Bukkit.getServer().consoleSender.sendMessage(Colors.colored("$prefix(&c!&f) $msg"))
        }
    }
}