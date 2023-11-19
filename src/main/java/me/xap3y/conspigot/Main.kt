package me.xap3y.conspigot

import me.xap3y.conspigot.Utils.Config
import me.xap3y.conspigot.Utils.Logger
import me.xap3y.conspigot.WS.WSServer
import org.bukkit.plugin.java.JavaPlugin
import java.net.InetSocketAddress


class Main : JavaPlugin() {
    private var server: WSServer? = null
    private var serverThread: Thread? = null
    override fun onEnable() {
        Logger.info("&aLoaded. &fMade by XAP3Y")

        val serverAddress = InetSocketAddress("0.0.0.0", Config.port)

        server = WSServer(serverAddress)

        serverThread = Thread {
            if (Config.debug) Logger.info("Socket Server starting..")
            server?.start()
            if (Config.debug) Logger.info("Socket Server started")
        }

        if (Config.debug) Logger.info("Starting Socket Server thread...")
        serverThread?.start()
        if (Config.debug) Logger.info("Socket server thread started")
    }

    override fun onDisable() {
        if (Config.debug) Logger.info("Closing server..")
        server?.stop(1000)
        serverThread = null
        if (Config.debug) Logger.info("Server closed!")
    }
}
