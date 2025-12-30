package post.repository

import post.Post
import kotlinx.datetime.Instant
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Temporary in-memory repository.
 * Will be replaced by a DB-backed implementation later.
 */
class InMemoryPostRepository : PostRepository {

    private val posts = CopyOnWriteArrayList<Post>()

    override suspend fun create(post: Post): Post {
        posts.add(post)
        return post
    }

    override suspend fun findById(postId: UUID): Post? {
        return posts.firstOrNull { it.id == postId }
    }

    override suspend fun findByHobby(
        hobbyId: UUID,
        limit: Int,
        cursor: Instant?
    ): List<Post> {
        return posts
            .asSequence()
            .filter { it.hobbyId == hobbyId }
            .filter { cursor == null || it.createdAt < cursor }
            .sortedByDescending { it.createdAt }
            .take(limit)
            .toList()
    }

    override suspend fun findByAuthor(
        authorId: UUID,
        limit: Int,
        cursor: Instant?
    ): List<Post> {
        return posts
            .asSequence()
            .filter { it.authorId == authorId }
            .filter { cursor == null || it.createdAt < cursor }
            .sortedByDescending { it.createdAt }
            .take(limit)
            .toList()
    }
}
