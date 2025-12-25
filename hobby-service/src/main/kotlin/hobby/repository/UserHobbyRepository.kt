package hobby.repository

import hobby.UserHobby

interface UserHobbyRepository {

    suspend fun join(userId: String, hobbyId: String)

    suspend fun leave(userId: String, hobbyId: String)

    suspend fun isMember(userId: String, hobbyId: String): Boolean

    suspend fun findHobbiesByUser(userId: String): List<UserHobby>
}
