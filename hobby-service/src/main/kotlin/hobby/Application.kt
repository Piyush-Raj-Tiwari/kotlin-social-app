package hobby

import hobby.repository.HobbyRepositoryImpl
import hobby.repository.UserHobbyRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8081) {
        module()
    }.start(wait = true)
}

fun Application.module() {

    install(ContentNegotiation) {
        json()
    }

    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.message))
        }
        exception<IllegalStateException> { call, cause ->
            call.respond(HttpStatusCode.Conflict, mapOf("error" to cause.message))
        }
    }

    val hobbyRepository = HobbyRepositoryImpl()
    val userHobbyRepository = UserHobbyRepositoryImpl()

    val hobbyService = HobbyService(
        hobbyRepository,
        userHobbyRepository
    )

    routing {
        get("/health") {
            call.respond("hobby-service is running")
        }
        hobbyRoutes(hobbyService)
    }
}
