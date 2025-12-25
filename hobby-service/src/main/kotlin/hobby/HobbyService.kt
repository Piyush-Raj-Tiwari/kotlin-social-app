package hobby

import hobby.repository.HobbyRepository
import hobby.repository.UserHobbyRepository
import java.time.Instant

class HobbyService(
    private val hobbyRepository: HobbyRepository,
    private val userHobbyRepository: UserHobbyRepository
) {

    suspend fun getAllHobbies(): List<Hobby> {
        return hobbyRepository.findAll()
    }

    suspend fun getHobbyById(hobbyId: String): Hobby {
        return hobbyRepository.findById(hobbyId)
            ?: throw IllegalArgumentException("Hobby not found")
    }

    suspend fun joinHobby(userId: String, hobbyId: String) {
        val hobby = hobbyRepository.findById(hobbyId)
            ?: throw IllegalArgumentException("Hobby not found")

        if (!hobby.isActive) {
            throw IllegalStateException("Hobby is not active")
        }

        val alreadyMember = userHobbyRepository.isMember(userId, hobbyId)
        if (alreadyMember) {
            return // idempotent join
        }

        userHobbyRepository.join(
            userId = userId,
            hobbyId = hobbyId
        )
    }

    suspend fun leaveHobby(userId: String, hobbyId: String) {
        userHobbyRepository.leave(userId, hobbyId)
    }

    suspend fun getUserHobbies(userId: String): List<Hobby> {
        val memberships = userHobbyRepository.findHobbiesByUser(userId)

        return memberships.mapNotNull { membership ->
            hobbyRepository.findById(membership.hobbyId)
        }
    }

    suspend fun hobbyExists(hobbyId: String): Boolean {
        return hobbyRepository.exists(hobbyId)
    }
}
