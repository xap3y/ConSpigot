package me.xap3y.statuer.Commands

import me.xap3y.statuer.Statuer
import me.xap3y.statuer.Utils.Colors
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class ReloadConfig(private val plugin: Statuer) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("Statuer", ignoreCase = true)) {
            if (sender.hasPermission("statuer.reload")) {
                plugin.reloadConfiguration()
                sender.sendMessage(Colors.colored("&fPlugin reloaded!"))
            } else {
                sender.sendMessage(Colors.colored(plugin.Config?.messages?.first()?.NoPermissions ?: "&cNoPermissions!"))
            }
            return true
        }
        return false
    }
}