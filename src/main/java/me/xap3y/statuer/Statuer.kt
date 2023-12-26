package me.xap3y.statuer
import me.xap3y.statuer.Commands.ReloadConfig
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Config.LoadConfig.Companion.LoadConfig
import me.xap3y.statuer.Utils.Lag
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.WS.WSServer
import org.bukkit.Bukkit
import org.bukkit.World;
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
        serverAddress = InetSocketAddress(Config!!.SocketAddress, Config!!.SocketPort)
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

    override fun onEnable() {
        getCommand("Statuer")?.executor = ReloadConfig(this)
        dataFolder.mkdir()

        Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(this, Lag(), 100L, Lag.TICK_INTERVAL)

        Config = LoadConfig(configFile)
        if (Config == null) Logger.info("&4There was error loading config file!!")

        if (Config != null) {

            serverAddress = InetSocketAddress(Config!!.SocketAddress, Config!!.SocketPort)

            server = WSServer(serverAddress, Config!!, this)

            serverThread = Thread {
                Logger.info(Config?.messages?.StartingWebsocket ?: "Websocket starting....")
                server?.start()
                Logger.info(
                    Config?.messages?.StartedWebsocket?.replace("%a", "&e(${Config!!.SocketAddress}:${Config!!.SocketPort})")
                        ?: "&a&lWebsocket started. &e(${Config!!.SocketAddress}:${Config!!.SocketPort})")
            }
            Logger.info(Config?.messages?.StartingThread ?: "Websocket thread starting....")
            serverThread?.start()
            Logger.info(Config?.messages?.StartedThread ?: "&a&lWebsocket thread started.")
        } else {
            Logger.info("&cConfig file is null!")
        }
    }

    override fun onDisable() {
        Logger.info("&cGoodBye :((( ")
        server?.stop(100)
        serverThread = null
    }


}


