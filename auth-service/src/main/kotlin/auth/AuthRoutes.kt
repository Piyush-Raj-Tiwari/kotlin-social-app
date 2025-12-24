package auth

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import auth.security.JwtProvider

fun Route.authRoutes(
    authService: AuthService,
    jwtProvider: JwtProvider
) {

    route("/auth") {

        post("/login") {
            val request = call.receive<LoginRequest>()
            val response = authService.login(
                request.email,
                request.password
            )
            call.respond(HttpStatusCode.OK, response)
        }

        get("/public-key") {
            call.respond(
                mapOf("publicKey" to jwtProvider.getPublicKeyBase64())
            )
        }
    }
}