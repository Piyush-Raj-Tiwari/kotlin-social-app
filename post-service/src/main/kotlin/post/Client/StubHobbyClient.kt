package post.client

import java.util.UUID

/**
 * Temporary stub implementation of HobbyClient.
 *
 * NOTE:
 * - Assumes all hobbies exist
 * - Used only for local development
 * - Will be replaced by an HTTP-based implementation later
 */
class StubHobbyClient : HobbyClient {

    override suspend fun hobbyExists(hobbyId: UUID): Boolean {
        return true
    }
}
