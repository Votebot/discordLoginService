package space.votebot.discordlogin.web.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import kotlinx.html.*
import space.votebot.discordlogin.config.Config
import space.votebot.discordlogin.core.DiscordAPI
import space.votebot.discordlogin.domain.services.UserService
import java.net.URLEncoder

class UserController(private val config: Config) {

    suspend fun signIn(ctx: ApplicationCall) {
        ctx.respondRedirect(
            "https://discordapp.com/api/oauth2/authorize?client_id=${config.clientId}&redirect_uri=${URLEncoder.encode(
                config.redirect,
                "UTF-8"
            )}&response_type=code&scope=guilds%20identify"
        )
    }

    suspend fun callback(ctx: ApplicationCall) {
        val code = ctx.request.queryParameters["code"]
        if (code == null) {
            ctx.respond(HttpStatusCode.BadRequest)
            return
        }
        val res = DiscordAPI.exchangeCode(config.clientId, config.clientSecret, config.redirect, code)
        val token = res!!["access_token"].toString()
        val user = DiscordAPI.getUser(token)
        val newToken = UserService.createToken(
            user!!["id"].toString(),
            token,
            res["refresh_token"].toString(),
            res["expires_in"].toString().toLong()
        )
        ctx.respondHtml {
            body {
                h1 {
                    +"Redirecting..."
                }
                script(type = ScriptType.textJavaScript) {
                    unsafe {
                        raw(
                            """
                     if (window.opener) {
                         window.opener.postMessage(${jacksonObjectMapper().writeValueAsString(
                                mapOf(
                                    "token" to newToken,
                                    "discord_token" to token
                                )
                            )}, '${config.windowOrigin}');
                         window.close();
                       }
                    """.trimIndent()
                        )
                    }
                }
            }
        }
    }
}