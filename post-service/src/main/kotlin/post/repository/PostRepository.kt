package post.repository

import post.Post
import kotlinx.datetime.Instant
import java.util.UUID

/**
 * Data access contract for Post Service.
 * Implementations may be in-memory or DB-backed.
 */
interface PostRepository {

    suspend fun create(post: Post): Post

    suspend fun findById(postId: UUID): Post?

    suspend fun findByHobby(
        hobbyId: UUID,
        limit: Int,
        cursor: Instant?
    ): List<Post>

    suspend fun findByAuthor(
        authorId: UUID,
        limit: Int,
        cursor: Instant?
    ): List<Post>
}
