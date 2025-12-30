package post

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Core domain entity owned by Post Service.
 * NOT serialized directly.
 */
data class Post(
    val id: UUID,
    val authorId: UUID,
    val hobbyId: UUID,
    val content: String,
    val createdAt: Instant,

    // Lightweight engagement signals
    val likeCount: Long = 0,
    val commentCount: Long = 0,

    /**
     * Reserved for future extensions (repost, quote, remix, etc.)
     * Not used by the system in the current phase.
     */
    val referencePostId: UUID? = null
)

/**
 * API request DTO.
 * Uses String instead of UUID for JSON compatibility.
 */
@Serializable
data class CreatePostRequest(
    val hobbyId: String,
    val content: String,
    val referencePostId: String? = null
)

/**
 * API response DTO.
 * Uses JSON-native types only.
 */
@Serializable
data class PostResponse(
    val id: String,
    val authorId: String,
    val hobbyId: String,
    val content: String,
    val createdAt: String,
    val likeCount: Long,
    val commentCount: Long,
    val referencePostId: String?
)

/**
 * Explicit boundary mapping from domain → API.
 */
fun Post.toResponse(): PostResponse =
    PostResponse(
        id = id.toString(),
        authorId = authorId.toString(),
        hobbyId = hobbyId.toString(),
        content = content,
        createdAt = createdAt.toString(),
        likeCount = likeCount,
        commentCount = commentCount,
        referencePostId = referencePostId?.toString()
    )
