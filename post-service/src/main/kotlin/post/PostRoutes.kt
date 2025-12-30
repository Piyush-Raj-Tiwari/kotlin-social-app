package post

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID
import kotlinx.datetime.Instant

fun Route.postRoutes(postService: PostService) {

    route("/posts") {

        /**
         * Create a new post
         */
        post {
            val userIdHeader = call.request.headers["X-User-Id"]
                ?: return@post call.respondText(
                    "Missing X-User-Id header",
                    status = io.ktor.http.HttpStatusCode.Unauthorized
                )

            val authorId = UUID.fromString(userIdHeader)
            val request = call.receive<CreatePostRequest>()

            val post = postService.createPost(
                authorId = authorId,
                hobbyId = UUID.fromString(request.hobbyId),
                content = request.content,
                referencePostId = request.referencePostId?.let(UUID::fromString)
            )

            call.respond(post.toResponse())
        }

        /**
         * Get post by ID
         */
        get("/{postId}") {
            val postId = UUID.fromString(call.parameters["postId"]!!)
            val post = postService.getPostById(postId)

            if (post == null) {
                call.respond(io.ktor.http.HttpStatusCode.NotFound)
            } else {
                call.respond(post.toResponse())
            }
        }

        /**
         * Get posts by hobby (paginated)
         */
        get("/hobby/{hobbyId}") {
            val hobbyId = UUID.fromString(call.parameters["hobbyId"]!!)
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
            val cursor = call.request.queryParameters["cursor"]
                ?.let { Instant.parse(it) }

            val posts = postService.getPostsByHobby(
                hobbyId = hobbyId,
                limit = limit,
                cursor = cursor
            )

            call.respond(posts.map { it.toResponse() })
        }

        /**
         * Get posts by user (paginated)
         */
        get("/user/{userId}") {
            val userId = UUID.fromString(call.parameters["userId"]!!)
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
            val cursor = call.request.queryParameters["cursor"]
                ?.let { Instant.parse(it) }

            val posts = postService.getPostsByAuthor(
                authorId = userId,
                limit = limit,
                cursor = cursor
            )

            call.respond(posts.map { it.toResponse() })
        }
    }
}
