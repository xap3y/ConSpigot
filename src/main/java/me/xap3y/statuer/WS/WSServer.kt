package me.xap3y.statuer.WS

// No idea what I just did, but it works

import com.google.gson.*
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Statuer
import me.xap3y.statuer.Utils.*
import me.xap3y.statuer.Utils.ResObjs.Companion.getErrorObjRes
import me.xap3y.statuer.Utils.ResObjs.Companion.getSuccessObjRes
import me.xap3y.statuer.Utils.Worlds.Companion.getWorlds
import me.xap3y.statuer.WS.modules.Player.Companion.kick
import me.xap3y.statuer.WS.modules.Player.Companion.permanentBan
import me.xap3y.statuer.WS.modules.Player.Companion.tempBan
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.File
import java.net.InetSocketAddress
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class WSServer(address: InetSocketAddress, private val Config: ConfigStructure, private val plugin: Statuer) : WebSocketServer(address) {
    private val connectionAliveMap = ConcurrentHashMap<WebSocket, Boolean>()
    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        if (Config.logLevel == 2) {
            Logger.info("WS Opened, conn address: " + conn.remoteSocketAddress /* + " sending JSON: " + getAll().toString()*/)
        }

        val token = sha512(UUID.randomUUID().toString())
        connectionAliveMap[conn] = false

        //Logger.info("WS TOKEN for conn $conn is $token")

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
        connectionAliveMap.remove(conn)
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
        //val token = connectionTokenMap[conn]
        val obj = convertToJson(message)
        if (obj == null) {
            sendToClient(conn, WSResObj()
                .addProperty("type", "error")
                .addProperty("cause", "Invalid request!")
                .build()
            )
            return
        }

        //Logger.info("message: $message")

        if (!checkPass(obj["password"].toString(), conn)) return

        val type = obj["type"].toString()

        if (type == "start_listener") {
            if (connectionAliveMap[conn] != true) {
                connectionAliveMap[conn] = true
                sendToClient(
                    conn, getSuccessObjRes("listener started"))
            } else {
                sendToClient(
                    conn, getErrorObjRes("Listener is already running"))
            }

        } else if (type == "end_listener") {
            if (connectionAliveMap[conn] == true) {
                connectionAliveMap[conn] = false
                sendToClient(conn, getSuccessObjRes("listener stopped"))
            } else {
                sendToClient(conn, getErrorObjRes("Listener is not running"))
            }


        } else if (type == "command"){
            if(!checkPass(obj["password"].toString(), conn)) return
            if(Utils.executeCMD(obj["command"].toString())){
                //Logger.info("CMD EXECUTED")

                sendToClient(conn, getSuccessObjRes("Command executed"))
            } else {
                Logger.info("CMD FAILED")
                sendToClient(conn, getErrorObjRes("Command execution failed"))
            }


        } else if (type == "player_kick") {
            sendToClient(conn, kick(obj))

        } else if (type == "player_permanentBan") {
            sendToClient(conn, permanentBan(obj))

        } else if (type == "player_permanentIpBan") {
            sendToClient(conn, permanentBan(obj, true))

        } else if (type == "player_tempBan") {
            sendToClient(conn, tempBan(obj, obj["duration"].toString().toInt()))

        } else if (type == "player_tempIpBan") {
            sendToClient(conn, tempBan(obj, obj["duration"].toString().toInt(), true))

        } else if (type == "get_ram") {
            sendToClient(conn, getSuccessObjRes(CPUMEMusage.getMemObj()))

        } else if (type == "get_tps") {
            sendToClient(conn, getSuccessObjRes(TPS.sendTPS()))

        } else if (type == "get_onlinePlayers") {
            sendToClient(conn, getSuccessObjRes(PlayerList.getOnlinePlayers()))

        } else if (type == "get_offlinePlayers") {
            sendToClient(conn, getSuccessObjRes(PlayerList.getOfflinePlayers()))

        } else if (type == "get_info") {
            //Logger.info("GetINFO")
            val allInOneObj = getAll()
            Logger.logFile(allInOneObj.toString(), File(plugin.dataFolder, "ws.txt"))
            sendToClient(conn, allInOneObj)
        } else {
            sendToClient(conn, getErrorObjRes("Bad request!"))
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
        allInObj.addProperty("error", false)
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
        allInObj.add("worlds", getWorlds())
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

    private fun checkPass(pass: String, conn: WebSocket? = null): Boolean {
        if (!Config.passRequired) return true
        val isPassValid = (Config.password == pass)
        if (conn != null && !isPassValid) {
            sendToClient(conn,getErrorObjRes("Invalid password!"))
        }
        return isPassValid
    }

    private fun convertToJson(message: String): Map<*, *>? {
        val gson = Gson()
        return try {
            gson.fromJson(message, Map::class.java)
        } catch (e: java.lang.Exception) {
            null
        }
    }
    fun broadcastMessage(obj: JsonObject? = null) {
        connectionAliveMap.keys.forEach { conn ->
            if (connectionAliveMap[conn] != true) {
                Logger.info("Connection is not alive, skipping... (${conn.remoteSocketAddress}/${conn.protocol})")
                return@forEach
            }
            if (obj != null) {
                sendToClient(conn, obj)
            } else {
                sendToClient(conn, getAll())
            }
        }
    }
}