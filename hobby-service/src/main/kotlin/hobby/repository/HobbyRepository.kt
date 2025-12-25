package hobby.repository

import hobby.Hobby

interface HobbyRepository {

    suspend fun findAll(): List<Hobby>

    suspend fun findById(hobbyId: String): Hobby?

    suspend fun exists(hobbyId: String): Boolean
}
