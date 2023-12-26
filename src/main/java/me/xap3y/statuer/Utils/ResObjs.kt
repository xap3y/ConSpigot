package me.xap3y.statuer.Utils

import com.google.gson.JsonObject

class ResObjs {
    companion object {
        fun getErrorObjRes(error: String): JsonObject {
            return WSResObj()
                .addProperty("error", true)
                .addProperty("cause", error)
                .build()
        }

        fun getSuccessObjRes(message: String): JsonObject {
            return WSResObj()
                .addProperty("error", false)
                .addProperty("message", message)
                .build()
        }
    }
}