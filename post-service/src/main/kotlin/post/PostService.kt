package post

import post.client.HobbyClient
import post.repository.PostRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

/**
 * Core business logic for Post Service.
 */
class PostService(
    private val postRepository: PostRepository,
    private val hobbyClient: HobbyClient
) {

    /**
     * Create a new post under a hobby.
     * No repost logic is applied yet.
     */
    suspend fun createPost(
        authorId: UUID,
        hobbyId: UUID,
        content: String,
        referencePostId: UUID? = null
    ): Post {

        // Validate hobby existence
        if (!hobbyClient.hobbyExists(hobbyId)) {
            throw IllegalArgumentException("Hobby does not exist: $hobbyId")
        }

        val post = Post(
            id = UUID.randomUUID(),
            authorId = authorId,
            hobbyId = hobbyId,
            content = content,
            createdAt = Clock.System.now(),
            likeCount = 0,
            commentCount = 0,
            referencePostId = referencePostId
        )

        return postRepository.create(post)
    }

    suspend fun getPostById(postId: UUID): Post? {
        return postRepository.findById(postId)
    }

    suspend fun getPostsByHobby(
        hobbyId: UUID,
        limit: Int,
        cursor: Instant?
    ): List<Post> {
        return postRepository.findByHobby(
            hobbyId = hobbyId,
            limit = limit,
            cursor = cursor
        )
    }

    suspend fun getPostsByAuthor(
        authorId: UUID,
        limit: Int,
        cursor: Instant?
    ): List<Post> {
        return postRepository.findByAuthor(
            authorId = authorId,
            limit = limit,
            cursor = cursor
        )
    }
}
