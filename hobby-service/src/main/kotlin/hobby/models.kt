package hobby

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant


@Serializable
data class Hobby(
    val id: String,          // e.g. "dance"
    val name: String,        // "Dance"
    val description: String, // short description
    val isActive: Boolean    // soft-disable support
)

@Serializable
data class UserHobby(
    val userId: String,
    val hobbyId: String,
    val joinedAt: Instant
)