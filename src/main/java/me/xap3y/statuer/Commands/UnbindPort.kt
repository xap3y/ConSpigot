package me.xap3y.statuer.Commands

import me.xap3y.statuer.Statuer
import me.xap3y.statuer.Utils.Colors
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class UnbindPort(private val plugin: Statuer) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("Portunbind", ignoreCase = true)) {
            if (sender.hasPermission("statuer.unbind") || sender.hasPermission("statuer.*")) {
                //plugin.unBindPort()
                //Config?.messages?.first()?.ConfigReload ?: "&aConfig reloaded"
                sender.sendMessage(Colors.colored("&aWebSocket port un-binded"))
            } else {
                sender.sendMessage(Colors.colored(plugin.Config?.messages?.NoPermissions ?: "&cNoPermissions!"))
            }
            return true
        }

        return false
    }
}