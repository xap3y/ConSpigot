package me.xap3y.statuer
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Config.LoadConfig.Companion.LoadConfig
import me.xap3y.statuer.Listeners.*
import me.xap3y.statuer.Utils.Lag
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.WS.WSServer
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.InetSocketAddress


class Statuer : JavaPlugin() {
    private val configFile = File(dataFolder, "config.json")
    private var server: WSServer? = null
    private var serverThread: Thread? = null
    private lateinit var serverAddress: InetSocketAddress

    var Config: ConfigStructure? = null

    /*fun reloadConfiguration() {

        Config = LoadConfig(configFile)

        if (Config == null) Logger.info("&4There was error loading config file!!")
        serverThread = null
        if (server != null) {
            server!!.stop(1000)
        }
        server = null;
        serverAddress = InetSocketAddress(Config!!.socketAddress, Config!!.socketPort)
        server = WSServer(serverAddress, Config!!, this)
        server!!.isReuseAddr = true
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
        server?.stop()
        server = null
        serverThread = null

    }*/

    override fun onEnable() {
        //getCommand("Statuer")?.executor = ReloadConfig(this)
        //getCommand("Portunbind")?.executor = UnbindPort(this)
        dataFolder.mkdir()

        Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(this, Lag(), 100L, Lag.TICK_INTERVAL)

        Config = LoadConfig(configFile)
        if (Config == null) return Logger.info("&4There was error loading config file!!")


        //Logger.info(Config!!.socketAddress + " <<")

        serverAddress = InetSocketAddress(Config!!.socketAddress, Config!!.socketPort)

        server = WSServer(serverAddress, Config!!, this)
        server!!.isReuseAddr = true

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

        getServer().pluginManager.registerEvents(PlayerListener(server!!, Config!!), this)
        getServer().pluginManager.registerEvents(CommandListener(server!!, Config!!), this)
        getServer().pluginManager.registerEvents(ChatListener(server!!, Config!!), this)
        getServer().pluginManager.registerEvents(GamemodeListener(server!!, Config!!), this)
        getServer().pluginManager.registerEvents(PunishListener(server!!, Config!!), this)
        getServer().pluginManager.registerEvents(FoodLevelChangeListener(server!!, Config!!), this)

    }

    override fun onDisable() {
        //Logger.info("&cDisabling plugin...")
        server?.stop(200)
        server = null
        serverThread = null
    }
}


