package post.client

import java.util.UUID

/**
 * Service-local client abstraction for interacting with Hobby Service.
 *
 * NOTE:
 * Currently local to Post Service.
 * May be extracted into a shared client module later
 * if multiple services require it.
 */
interface HobbyClient {
    suspend fun hobbyExists(hobbyId: UUID): Boolean
}
