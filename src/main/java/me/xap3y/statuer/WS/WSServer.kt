package me.xap3y.statuer.WS

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.xap3y.statuer.Config.ConfigStructure
import me.xap3y.statuer.Utils.Logger
import me.xap3y.statuer.Utils.Utils
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress


class WSServer(address: InetSocketAddress, private val Config: ConfigStructure) : WebSocketServer(address) {
    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        if (Config.logLevel == 2) {
            Logger.info("WS Opened, conn address: " + conn.remoteSocketAddress + " sending JSON: " + getAll().toString())
        }
        sendToClient(conn, getAll())
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
        whileCon.start()
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        if (Config.logLevel == 2) {
            Logger.info("WS Closed, conn address: " + conn.remoteSocketAddress)
        }
        val errorObj = JsonObject()
        errorObj.addProperty("error", "host server closed")
        errorObj.addProperty("error_type", "big_error")
        sendToClient(conn, errorObj)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        Logger.info(message)
        val obj = JsonParser.parseString(message).asJsonObject
        val type = obj.get("type").asString
        if (type == "command"){
            Logger.info("Got command message")
            if(Utils.executeCMD(obj.get("command").asString)){
                Logger.info("CMD EXECUTED")
                val errorObj = JsonObject()
                errorObj.addProperty("success", "Command execution done")
                errorObj.addProperty("success_type", "cmd_done")
                sendToClient(conn, errorObj)
            } else {
                Logger.info("CMD FAILED")
                sendToClient(conn, Utils.errObj("Command execution failed", "cmd_error"))
            }
        } else if (type == "player_kick"){
            val response = PlayerActions.Kick(obj.get("player").asString, obj.get("reason").asString)
            if (response != "1"){
                sendToClient(conn, Utils.errObj(response, "kick_error"))
            } else {
                sendToClient(conn, Utils.doneObj("Player was kicked", "player_kicked"))
            }
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        val errorObj = JsonObject()
        errorObj.addProperty("error", ex.message)
        if(conn != null) sendToClient(conn, errorObj)
        Logger.error("WSServer error! &f$ex")
    }

    override fun onStart() {
        Logger.info("Socket opened")
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
        allInObj.add("server", ServerInfo.getInfo())
        allInObj.add("tps", TPS.sendTPS())
        allInObj.addProperty("cpu", CPUMEMusage.getCpuUsage())
        allInObj.add("mem", CPUMEMusage.getMemObj())
        allInObj.add("onlinePlayers", PlayerList.getOnlinePlayers())
        allInObj.add("offlinePlayers", PlayerList.getOfflinePlayers())
        return allInObj
    }
}