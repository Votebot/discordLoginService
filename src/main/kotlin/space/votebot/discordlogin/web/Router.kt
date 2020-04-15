package space.votebot.discordlogin.web

import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import space.votebot.discordlogin.web.controllers.UserController

fun Routing.users(userController: UserController) {
    get("signIn") { userController.signIn(this.context)}
    get("signIn/callback") { userController.callback(this.context)}
}