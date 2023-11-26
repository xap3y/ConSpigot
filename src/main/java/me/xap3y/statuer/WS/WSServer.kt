package me.xap3y.statuer.WS

import com.google.gson.Gson
import com.google.gson.JsonElement
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
            Logger.info("WS Opened, conn address: " + conn.remoteSocketAddress /* + " sending JSON: " + getAll().toString()*/)
        }
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
        val errorObj = JsonObject()
        errorObj.addProperty("error", "host server closed")
        errorObj.addProperty("error_type", "big_error")
        sendToClient(conn, errorObj)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        //Logger.info(message)
        val gson = Gson()
        val obj = gson.fromJson(message, Map::class.java)
        val type = obj["type"].toString()

        if (type == "command"){
            Logger.info("Got command message")
            if(Utils.executeCMD(obj["command"].toString())){
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
            val response = PlayerActions.Kick(obj["player"].toString(), obj["reason"].toString())
            if (response != "1"){
                sendToClient(conn, Utils.errObj(response, "kick_error"))
            } else {
                sendToClient(conn, Utils.doneObj("Player was kicked", "player_kicked"))
            }


        } else if (type == "get_info") {
            //Logger.info("GetINFO")
            val password = obj["password"].toString()
            if (Config.passRequired) {
                if (Config.password != password) {
                    //Logger.info("PASSWORD DOESNT MATCH")
                    val errorObj = JsonObject()
                    errorObj.addProperty("error", "invalid password")
                    errorObj.addProperty("error_type", "no_auth")
                    sendToClient(conn, errorObj)
                }
                else {
                    sendToClient(conn, getAll())
                }
            }
            else {
                sendToClient(conn, getAll())
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
        //allInObj.add("tps", TPS.sendTPS())
        allInObj.addProperty("cpu", CPUMEMusage.getCpuUsage())
        allInObj.add("mem", CPUMEMusage.getMemObj())
        allInObj.add("onlinePlayers", PlayerList.getOnlinePlayers())
        allInObj.add("offlinePlayers", PlayerList.getOfflinePlayers())
        return allInObj
    }
}