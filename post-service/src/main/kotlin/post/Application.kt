package post

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import post.client.StubHobbyClient
import post.repository.InMemoryPostRepository

fun main() {
    embeddedServer(Netty, port = 8082, module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    // ---- Plugins ----
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

    // ---- Wiring ----
    val postRepository = InMemoryPostRepository()
    val hobbyClient = StubHobbyClient()
    val postService = PostService(
        postRepository = postRepository,
        hobbyClient = hobbyClient
    )

    // ---- Routes ----
    routing {

        get("/health"){
            call.respond(HttpStatusCode.OK, "Post service is running")
        }
        postRoutes(postService)
    }
}
