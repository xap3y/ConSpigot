package me.xap3y.statuer
import me.xap3y.statuer.Commands.ReloadConfig
import me.xap3y.statuer.Commands.UnbindPort
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Config.LoadConfig.Companion.LoadConfig
import me.xap3y.statuer.Listeners.ChatListener
import me.xap3y.statuer.Listeners.CommandListener
import me.xap3y.statuer.Listeners.PlayerListener
import me.xap3y.statuer.Utils.Lag
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.WS.WSServer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.InetSocketAddress


class Statuer : JavaPlugin() {
    private val configFile = File(dataFolder, "config.json")
    val loggingFile = File(dataFolder, "logs.txt")
    private var server: WSServer? = null
    private var serverThread: Thread? = null
    private lateinit var serverAddress: InetSocketAddress

    var Config: ConfigStructure? = null

    fun reloadConfiguration() {

        Config = LoadConfig(configFile)

        if (Config == null) Logger.info("&4There was error loading config file!!")
        serverThread = null
        if (server != null) {
            server!!.stop(1000)
        }
        server = null;
        serverAddress = InetSocketAddress(Config!!.socketAddress, Config!!.socketPort)
        server = WSServer(serverAddress, Config!!, this)
        serverThread = Thread {
            //Logger.info(Config?.messages?.first()?.StartingWebsocket ?: "Websocket starting....")
            server?.start()
            //Logger.info(Config?.messages?.first()?.StartedWebsocket?.replace("%a", "&e(${Config!!.SocketAddress}:${Config!!.SocketPort})") ?: "&a&lWebsocket started. &e(${Config!!.SocketAddress}:${Config!!.SocketPort})")
        }

        //Logger.info(Config?.messages?.first()?.StartingThread ?: "Websocket thread starting....")
        serverThread?.start()
        //Logger.info(Config?.messages?.first()?.StartedThread ?: "&a&lWebsocket thread started.")

        //Logger.info(Config?.messages?.first()?.ConfigReload ?: "&aConfig reloaded")
    }

    fun unBindPort() {
        server?.stop(100)
        if (server != null) {
            server!!.stop(1000)
        }
        server = null;
        serverThread = null

    }

    override fun onEnable() {
        getCommand("Statuer")?.executor = ReloadConfig(this)
        getCommand("Portunbind")?.executor = UnbindPort(this)
        dataFolder.mkdir()

        Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(this, Lag(), 100L, Lag.TICK_INTERVAL)

        Config = LoadConfig(configFile)
        if (Config == null) Logger.info("&4There was error loading config file!!")

        if (Config != null) {

            Logger.info(Config!!.socketAddress + " <<")

            serverAddress = InetSocketAddress(Config!!.socketAddress, Config!!.socketPort)

            server = WSServer(serverAddress, Config!!, this)

            serverThread = Thread {
                Logger.info(Config?.messages?.StartingWebsocket ?: "Websocket starting....")
                server?.start()
                Logger.info(
                    Config?.messages?.StartedWebsocket?.replace("%a", "&e(${Config!!.socketAddress}:${Config!!.socketPort})")
                        ?: "&a&lWebsocket started. &e(${Config!!.socketAddress}:${Config!!.socketPort})")
            }
            Logger.info(Config?.messages?.startingThread ?: "Websocket thread starting....")
            serverThread?.start()
            Logger.info(Config?.messages?.StartedThread ?: "&a&lWebsocket thread started.")
        } else {
            Logger.info("&cConfig file is null!")
        }

        getServer().pluginManager.registerEvents(PlayerListener(server!!), this)
        getServer().pluginManager.registerEvents(CommandListener(server!!), this)
        getServer().pluginManager.registerEvents(ChatListener(server!!), this)

    }

    override fun onDisable() {
        Logger.info("&cGoodBye :((( ")
        server?.stop(200)
        server = null
        serverThread = null
    }
}


