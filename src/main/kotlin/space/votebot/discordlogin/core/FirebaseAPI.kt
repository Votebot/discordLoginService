package space.votebot.discordlogin.core

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import space.votebot.discordlogin.config.Config
import java.io.FileInputStream

class FirebaseAPI(config: Config) {
    init {
        val serviceAccount = FileInputStream(config.keyPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(config.dbUrl)
            .build()
        FirebaseApp.initializeApp(options)
    }
}