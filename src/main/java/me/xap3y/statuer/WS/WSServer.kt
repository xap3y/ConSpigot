package me.xap3y.statuer.WS

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Statuer
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.Utils.Utils
import me.xap3y.statuer.Utils.WSResObj
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.File
import java.net.InetSocketAddress
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import java.util.UUID;

class WSServer(address: InetSocketAddress, private val Config: ConfigStructure, private val plugin: Statuer) : WebSocketServer(address) {
    private val connectionTokenMap = ConcurrentHashMap<WebSocket, String>()
    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        if (Config.logLevel == 2) {
            Logger.info("WS Opened, conn address: " + conn.remoteSocketAddress /* + " sending JSON: " + getAll().toString()*/)
        }
        val token = sha512(UUID.randomUUID().toString())
        connectionTokenMap[conn] = token

        Logger.info("WS TOKEN for conn $conn is $token")

        sendToClient(conn, WSResObj()
            .addProperty("type", "success")
            .addProperty("response", "con_opened")
            .addProperty("token", token)
            .build()
        )

        /*sendToClient(conn, getAll())
        val whileCon = Thread {
            var oldGet = getAll().toString()
            while(conn.isOpen) {
                if (oldGet != getAll().toString()) {
                    sendToClient(conn, getAll())
                    oldGet = getAll().toString()
                }
                Thread.sleep(1000)
            }
        }
        whileCon.start()*/
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        if (Config.logLevel == 2) {
            Logger.info("WS Closed, conn address: " + conn.remoteSocketAddress)
        }

        sendToClient(conn, WSResObj()
            .addProperty("type", "error")
            .addProperty("cause", "con_closed")
            .build()
        )
    }

    override fun onMessage(conn: WebSocket, message: String) {
        //Logger.info("TPS:")
        //Logger.info(TPS.sendTPS().toString())
        val token = connectionTokenMap[conn]
        val gson = Gson()
        val obj: Map<*, *> =
        try {
            gson.fromJson(message, Map::class.java)
        } catch (e: java.lang.Exception) {
            sendToClient(conn, WSResObj()
                .addProperty("type", "error")
                .addProperty("cause", "Invalid message JSON")
                .build()
            )
            return
        }

        Logger.info("message: $message")

        val type = obj["type"].toString()

        if (type == "command"){
            Logger.info("Got command message")
            if(Utils.executeCMD(obj["command"].toString())){
                Logger.info("CMD EXECUTED")

                sendToClient(conn, WSResObj()
                    .addProperty("type", "success")
                    .addProperty("response", "Command execution done")
                    .build()
                )
            } else {
                Logger.info("CMD FAILED")
                sendToClient(conn, WSResObj()
                    .addProperty("type", "error")
                    .addProperty("cause", "Command execution failed")
                    .build()
                )
            }


        } else if (type == "player_kick"){
            val response = PlayerActions.Kick(obj["player"].toString(), obj["reason"].toString())
            if (response != "1"){
                sendToClient(conn, WSResObj()
                    .addProperty("type", "error")
                    .addProperty("cause", "Cannot kick player")
                    .build()
                )
            } else {
                sendToClient(conn, WSResObj()
                    .addProperty("type", "success")
                    .addProperty("response", "Player was kicked")
                    .build()
                )
            }


        } else if (type == "get_info") {
            //Logger.info("GetINFO")
            val password = obj["password"].toString()
            if (Config.passRequired && Config.password != password) {
                //Logger.info("PASSWORD DOESNT MATCH")
                sendToClient(conn, WSResObj()
                    .addProperty("type", "error")
                    .addProperty("cause", "invalid password")
                    .build()
                )
                return
            }
            val allInOneObj = getAll()
            Logger.logFile(allInOneObj.toString(), File(plugin.dataFolder, "ws.txt"))
            sendToClient(conn, allInOneObj)
        } else {
            sendToClient(conn, WSResObj()
                .addProperty("type", "error")
                .addProperty("cause", "bad request!")
                .build()
            )
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        val errorObj = JsonObject()
        errorObj.addProperty("error", ex.message)
        if(conn != null) sendToClient(conn, errorObj)
        Logger.error("WSServer error! &f$ex")
    }

    override fun onStart() {
        if(Config.logLevel >= 2) Logger.info("Socket opened")
    }

    private fun sendToClient(conn: WebSocket, content: JsonObject) {
        try {
            conn.send(content.toString())
        } catch (ex: Exception) {
            // Ignore
        }
    }

    private fun getAll(): JsonObject {
        val allInObj = JsonObject()
        allInObj.addProperty("type", "success")
        val _temp = Config.modules
        if(
            _temp.serverName ||
            _temp.serverIP ||
            _temp.serverPort ||
            _temp.serverVersion ||
            _temp.bukkitVersion ||
            _temp.maxPlayers ||
            _temp.currentPlayers
            ) allInObj.add("server", ServerInfo.getInfo(Config))
        if(_temp.tps) allInObj.add("tps", TPS.sendTPS())
        if(_temp.cpuLoad) allInObj.addProperty("cpu", CPUMEMusage.getCpuUsage())
        if(_temp.memory) allInObj.add("mem", CPUMEMusage.getMemObj())
        if(_temp.onlinePlayers) allInObj.add("onlinePlayers", PlayerList.getOnlinePlayers())
        if(_temp.offlinePlayers) allInObj.add("offlinePlayers", PlayerList.getOfflinePlayers())
        return allInObj
    }

    private fun sha512(input: String): String {
        return MessageDigest.getInstance("SHA-512")
            .digest(input.toByteArray())
            .joinToString(separator = "") {
                ((it.toInt() and 0xff) + 0x100)
                    .toString(16)
                    .substring(1)
            }
    }
}