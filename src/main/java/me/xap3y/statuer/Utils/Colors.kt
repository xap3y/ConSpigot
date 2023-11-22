package me.xap3y.statuer.Utils

import org.bukkit.ChatColor

class Colors {
    companion object {
        fun colored(msg: String): String {
            return ChatColor.translateAlternateColorCodes('&', msg);
        }
    }
}