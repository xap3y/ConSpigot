package me.xap3y.statuer.Utils

import com.google.gson.JsonObject

class WSResObj {
    private val jsonObject = JsonObject()

    fun addProperty(key: String, value: String): WSResObj {
        jsonObject.addProperty(key, value)
        return this
    }

    fun addProperty(key: String, value: Number): WSResObj {
        jsonObject.addProperty(key, value)
        return this
    }

    fun build(): JsonObject {
        return jsonObject
    }
}