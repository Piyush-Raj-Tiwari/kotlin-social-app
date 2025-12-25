package hobby

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.hobbyRoutes(hobbyService: HobbyService) {

    route("/hobbies") {

        get {
            val hobbies = hobbyService.getAllHobbies()
            call.respond(hobbies)
        }

        get("/{hobbyId}") {
            val hobbyId = call.parameters["hobbyId"]
                ?: throw IllegalArgumentException("Missing hobbyId")

            val hobby = hobbyService.getHobbyById(hobbyId)
            call.respond(hobby)
        }

        post("/{hobbyId}/join") {
            val hobbyId = call.parameters["hobbyId"]
                ?: throw IllegalArgumentException("Missing hobbyId")

            // TEMP: later this comes from auth middleware
            val userId = call.request.headers["X-User-Id"]
                ?: throw IllegalArgumentException("Missing userId")

            hobbyService.joinHobby(userId, hobbyId)
            call.respondText("Joined")
        }

        post("/{hobbyId}/leave") {
            val hobbyId = call.parameters["hobbyId"]
                ?: throw IllegalArgumentException("Missing hobbyId")

            val userId = call.request.headers["X-User-Id"]
                ?: throw IllegalArgumentException("Missing userId")

            hobbyService.leaveHobby(userId, hobbyId)
            call.respondText("Left")
        }
    }

    route("/users/{userId}/hobbies") {
        get {
            val userId = call.parameters["userId"]
                ?: throw IllegalArgumentException("Missing userId")

            val hobbies = hobbyService.getUserHobbies(userId)
            call.respond(hobbies)
        }
    }
}
