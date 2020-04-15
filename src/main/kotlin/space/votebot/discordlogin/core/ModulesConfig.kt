package space.votebot.discordlogin.core

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import space.votebot.discordlogin.config.Config
import space.votebot.discordlogin.domain.services.UserService
import space.votebot.discordlogin.web.controllers.UserController

object ModulesConfig {
    private val configModule = Kodein.Module("CONFIG") {
        bind() from singleton { Config() }
    }

    private val userModule = Kodein.Module("USER") {
        bind() from singleton { UserController(instance()) }
    }

    internal val kodein = Kodein {
        import(configModule)
        import(userModule)
    }
}