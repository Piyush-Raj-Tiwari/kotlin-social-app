package auth.repository

import auth.User
import java.util.UUID

class UserRepository {

    private val users = listOf(
        User(
            id = UUID.randomUUID(),
            email = "test@test.com",
            // hash of "password"
            passwordHash = "\$2a\$10\$7qXkZQ7KQm1zYyQh1fQ1e.6X7v0ZtJ0zX8Y8zqvYw0ZxkFzKxZ1vW",
            role = "USER"
        )
    )

    fun findByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}
