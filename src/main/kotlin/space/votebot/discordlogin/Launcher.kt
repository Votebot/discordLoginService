package space.votebot.discordlogin

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import io.sentry.Sentry
import org.slf4j.LoggerFactory
import space.votebot.discordlogin.config.Config
import space.votebot.discordlogin.config.Environment
import space.votebot.discordlogin.core.DiscordLoginService

fun main() {
    val cfg = Config()
    val logLevel = Level.valueOf(cfg.logLevel)

    val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
    rootLogger.level = logLevel

    if (cfg.environment != Environment.DEVELOPMENT) {
        Sentry.init("${cfg.sentryDSN}?environment=${cfg.environment}")
    } else {
        Sentry.init()
    }

    DiscordLoginService(cfg).start()
}
