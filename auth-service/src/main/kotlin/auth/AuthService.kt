package auth

import auth.repository.UserRepository
import auth.security.PasswordHasher
import auth.security.JwtProvider

class AuthService(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher,
    private val jwtProvider: JwtProvider
) {

    fun login(email: String, password: String): LoginResponse {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid credentials")

        val valid = passwordHasher.verify(password, user.passwordHash)
        if (!valid) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = jwtProvider.generateAccessToken(
            userId = user.id,
            email = user.email,
            role = user.role
        )

        return LoginResponse(
            accessToken = token
        )
    }
}
