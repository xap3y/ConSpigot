package me.xap3y.statuer
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Commands.ReloadConfig
import me.xap3y.statuer.Config.LoadConfig.Companion.LoadConfig
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.WS.WSServer
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.InetSocketAddress

class Statuer : JavaPlugin() {
    private val configFile = File(dataFolder, "config.json")
    private var server: WSServer? = null
    private var serverThread: Thread? = null

    var Config: ConfigStructure? = null

    fun reloadConfiguration() {

        Config = LoadConfig(configFile)

        if (Config == null) Logger.info("&4There was error loading config file!!")
        serverThread = null
        if (server != null) {
            server!!.stop(1000)
        }
        server = null;
        serverThread = Thread {
            Logger.info(Config?.messages?.first()?.StartingWebsocket ?: "Websocket starting....")
            server?.start()
            Logger.info(Config?.messages?.first()?.StartedWebsocket?.replace("%a", "&e(${Config!!.SocketAddress}:${Config!!.SocketPort})") ?: "&a&lWebsocket started. &e(${Config!!.SocketAddress}:${Config!!.SocketPort})")
        }

        Logger.info(Config?.messages?.first()?.StartingThread ?: "Websocket thread starting....")
        serverThread?.start()
        Logger.info(Config?.messages?.first()?.StartedThread ?: "&a&lWebsocket thread started.")

        Logger.info(Config?.messages?.first()?.ConfigReload ?: "&aConfig reloaded")
    }

    override fun onEnable() {
        getCommand("Statuer")?.executor = ReloadConfig(this)
        dataFolder.mkdir()


        Config = LoadConfig(configFile)
        if (Config == null) Logger.info("&4There was error loading config file!!")

        if (Config != null) {

            val serverAddress = InetSocketAddress(Config!!.SocketAddress, Config!!.SocketPort)

            server = WSServer(serverAddress, Config!!)

            serverThread = Thread {
                Logger.info(Config?.messages?.first()?.StartingWebsocket ?: "Websocket starting....")
                server?.start()
                Logger.info(
                    Config?.messages?.first()?.StartedWebsocket?.replace("%a", "&e(${Config!!.SocketAddress}:${Config!!.SocketPort})")
                        ?: "&a&lWebsocket started. &e(${Config!!.SocketAddress}:${Config!!.SocketPort})")
            }
            Logger.info(Config?.messages?.first()?.StartingThread ?: "Websocket thread starting....")
            serverThread?.start()
            Logger.info(Config?.messages?.first()?.StartedThread ?: "&a&lWebsocket thread started.")
        } else {
            Logger.info("&cConfig file is null!")
        }
    }

    override fun onDisable() {
        Logger.info("&cGoodBye :((( ")
    }
}


