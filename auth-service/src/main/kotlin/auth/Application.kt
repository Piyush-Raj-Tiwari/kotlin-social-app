package auth

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import auth.repository.UserRepository
import auth.security.PasswordHasher
import auth.security.JwtProvider
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.*

fun main() {
    embeddedServer(
        Netty,
        port = 8081, // IMPORTANT: different from task-service
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {

    install(ContentNegotiation) {
        json()
    }

    install(StatusPages) {

        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                mapOf("error" to (cause.message ?: "invalid request"))
            )
        }

        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "internal server error")
            )
        }
    }

    val userRepository = UserRepository()
    val passwordHasher = PasswordHasher()
    val jwtProvider = JwtProvider()

    val authService = AuthService(
        userRepository,
        passwordHasher,
        jwtProvider
    )

    routing {
        get("/health") {
            call.respondText("auth-service is running")
        }

        authRoutes(authService, jwtProvider)
    }
}
