package space.votebot.discordlogin.config

enum class Environment(val debug: Boolean) {
    DEVELOPMENT(true),
    CANARY(false),
    PRODUCTION(false);
}
