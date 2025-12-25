package hobby.repository

import hobby.UserHobby
import kotlinx.datetime.Clock
import java.util.concurrent.ConcurrentHashMap

class UserHobbyRepositoryImpl : UserHobbyRepository {

    private val userHobbies = ConcurrentHashMap<String, MutableSet<String>>()

    override suspend fun join(userId: String, hobbyId: String) {
        userHobbies
            .computeIfAbsent(userId) { mutableSetOf() }
            .add(hobbyId)
    }

    override suspend fun leave(userId: String, hobbyId: String) {
        userHobbies[userId]?.remove(hobbyId)
    }

    override suspend fun isMember(userId: String, hobbyId: String): Boolean =
        userHobbies[userId]?.contains(hobbyId) == true

    override suspend fun findHobbiesByUser(userId: String): List<UserHobby> {
        val hobbies = userHobbies[userId] ?: return emptyList()

        return hobbies.map {
            UserHobby(
                userId = userId,
                hobbyId = it,
                joinedAt = Clock.System.now()
            )
        }
    }
}
