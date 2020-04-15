package space.votebot.discordlogin.config

import ch.qos.logback.classic.Level
import io.github.cdimascio.dotenv.dotenv

class Config {

    private val dotenv = dotenv {
        ignoreIfMissing = true
    }

    val logLevel: String = dotenv["${PREFIX}LOG_LEVEL"]?.toUpperCase() ?: Level.INFO.levelStr
    val sentryDSN: String = dotenv["${PREFIX}SENTRY_DSN"] ?: ""
    val environment: Environment =
        Environment.valueOf(dotenv["${PREFIX}ENVIRONMENT"] ?: Environment.PRODUCTION.toString())
    val httpPort = dotenv["${PREFIX}HTTP_PORT"]?.toInt() ?: 6354
    val clientId = dotenv["${PREFIX}CLIENT_ID"] ?: ""
    val clientSecret = dotenv["${PREFIX}CLIENT_SECRET"] ?: ""
    val redirect = dotenv["${PREFIX}REDIRECT"] ?: ""
    val keyPath = dotenv["${PREFIX}FIREBASE_KEY"] ?: "firebase-admin.json"
    val dbUrl = dotenv["${PREFIX}DB_URL"] ?: ""
    val corsHost = dotenv["${PREFIX}CORS_HOST"] ?: "*"
    val windowOrigin = dotenv["${PREFIX}WINDOW_HOST"] ?: "*"

    companion object {
        private const val PREFIX = "SERVICE_"
    }
}
