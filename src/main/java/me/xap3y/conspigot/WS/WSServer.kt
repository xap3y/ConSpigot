package me.xap3y.conspigot.WS

import com.google.gson.JsonObject
import me.xap3y.conspigot.Utils.Config
import me.xap3y.conspigot.Utils.Logger
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class WSServer(address: InetSocketAddress) : WebSocketServer(address) {
    override fun onOpen(conn: org.java_websocket.WebSocket, handshake: ClientHandshake) {
        if (Config.debug) {
            Logger.info("WS Opened, conn address: " + conn.remoteSocketAddress + " sending JSON: " + getAll().toString())
        }
        conn.send(getAll().toString());
    }

    override fun onClose(conn: org.java_websocket.WebSocket, code: Int, reason: String, remote: Boolean) {
        if (Config.debug) {
            Logger.info("WS Closed, conn address: " + conn.remoteSocketAddress)
        }
    }

    override fun onMessage(conn: org.java_websocket.WebSocket, message: String) {
        if (Config.debug) {
            Logger.info("WS onMessage, conn address: " + conn.remoteSocketAddress + " message: " + message)
        }
    }

    override fun onError(conn: org.java_websocket.WebSocket?, ex: Exception) {
        Logger.error("WSServer error! &f$ex")
    }

    override fun onStart() {
        Logger.info("Socket opened");
    }

    private fun getAll(): JsonObject {
        val allInObj = JsonObject()
        allInObj.add("server", ServerInfo.getInfo())
        allInObj.add("tps", TPS.sendTPS())
        allInObj.addProperty("cpu", CPUusage.getCpuUsage())
        allInObj.add("mem", CPUusage.getMemObj())
        allInObj.add("onlinePlayers", PlayerList.getOnlinePlayers())
        allInObj.add("offlinePlayers", PlayerList.getOfflinePlayers())
        return allInObj
    }
}