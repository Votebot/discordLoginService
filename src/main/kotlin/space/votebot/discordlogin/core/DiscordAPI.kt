package space.votebot.discordlogin.core

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.firebase.auth.FirebaseAuth
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import space.votebot.discordlogin.config.Config

object DiscordAPI {

    private const val DISCORD_API = "https://discordapp.com/api"
    private val client = OkHttpClient()

    fun getUser(token: String): Map<*, *>? {
        val res = client.newCall(Request.Builder().apply {
            url("${DISCORD_API}/users/@me")
            addHeader("Authorization", "Bearer $token")
        }.build()).execute()
        res.body ?: return null
        return jacksonObjectMapper().readValue(res.body!!.string(), Map::class.java)
    }

    fun exchangeCode(clientId: String, clientSecret: String, redirect: String, code: String): Map<*, *>? {
        val reqBody = FormBody.Builder()
            .add("client_id", clientId)
            .add("client_secret", clientSecret)
            .add("grant_type", "authorization_code")
            .add("code", code)
            .add("redirect_uri", redirect)
            .add("scope", "identify guilds")
            .build()
        val req = Request.Builder()
            .url("${DISCORD_API}/oauth2/token")
            .post(reqBody)
            .build()
        val res = client.newCall(req).execute()
        res.body ?: return null
        return jacksonObjectMapper().readValue(res.body!!.string(), Map::class.java)
    }
}