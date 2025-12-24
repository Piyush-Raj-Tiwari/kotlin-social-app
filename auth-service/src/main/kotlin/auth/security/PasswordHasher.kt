package auth.security

import org.mindrot.jbcrypt.BCrypt

class PasswordHasher {

    fun hash(rawPassword: String): String {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }

    fun verify(rawPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(rawPassword, hashedPassword)
    }
}
