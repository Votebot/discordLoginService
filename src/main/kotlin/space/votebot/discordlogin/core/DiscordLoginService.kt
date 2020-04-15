package space.votebot.discordlogin.core

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging
import org.kodein.di.generic.instance
import space.votebot.discordlogin.config.Config
import space.votebot.discordlogin.domain.services.UserService
import space.votebot.discordlogin.util.logger
import space.votebot.discordlogin.web.controllers.UserController
import space.votebot.discordlogin.web.users
import java.io.FileInputStream

class DiscordLoginService(config: Config) {
    private val userController by ModulesConfig.kodein.instance<UserController>()
    private val server = embeddedServer(Netty, config.httpPort) {
        install(CORS) {
            host(config.corsHost)
            allowCredentials = true
        }
        install(Routing) {
            users(userController)
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                logger().error { cause }
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }

    init {
        val serviceAccount = FileInputStream(config.keyPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(config.dbUrl)
            .build()
        FirebaseApp.initializeApp(options)
    }

    fun start() {
        server.start(wait = true)
    }
}