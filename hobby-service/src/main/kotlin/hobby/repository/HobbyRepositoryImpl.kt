package hobby.repository

import hobby.Hobby
import java.util.concurrent.ConcurrentHashMap

class HobbyRepositoryImpl : HobbyRepository {

    private val hobbies = ConcurrentHashMap<String, Hobby>()

    init {
        hobbies["dance"] = Hobby(
            id = "dance",
            name = "Dance",
            description = "All forms of dance",
            isActive = true
        )
        hobbies["music"] = Hobby(
            id = "music",
            name = "Music",
            description = "All kinds of music",
            isActive = true
        )
    }

    override suspend fun findAll(): List<Hobby> =
        hobbies.values.toList()

    override suspend fun findById(hobbyId: String): Hobby? =
        hobbies[hobbyId]

    override suspend fun exists(hobbyId: String): Boolean =
        hobbies.containsKey(hobbyId)
}
