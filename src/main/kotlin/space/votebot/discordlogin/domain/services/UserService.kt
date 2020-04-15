package space.votebot.discordlogin.domain.services

import com.google.firebase.auth.FirebaseAuth

object UserService {

    fun createToken(id: String, accessToken: String, refreshToken: String, expiresIn: Long): String {
        return FirebaseAuth.getInstance().createCustomToken(id, mapOf("access_token" to accessToken, "refresh_token" to refreshToken, "expires_in" to expiresIn))
    }
}