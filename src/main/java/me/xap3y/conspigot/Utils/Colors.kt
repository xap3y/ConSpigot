package me.xap3y.conspigot.Utils
import org.bukkit.ChatColor

class Colors {
    companion object {
        fun colored(msg: String): String {
            return ChatColor.translateAlternateColorCodes('&', msg);
        }
    }
}