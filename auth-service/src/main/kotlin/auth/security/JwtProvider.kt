package auth.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.KeyPair
import java.security.PublicKey
import java.util.Date
import java.util.UUID
import java.util.Base64

class JwtProvider {

    // In production, load this from keystore or env
    private val keyPair: KeyPair =
        Keys.keyPairFor(SignatureAlgorithm.RS256)

    private val issuer = "auth-service"
    private val accessTokenValidityMs = 15 * 60 * 1000 // 15 minutes

    fun generateAccessToken(
        userId: UUID,
        email: String,
        role: String
    ): String {

        val now = Date()
        val expiry = Date(now.time + accessTokenValidityMs)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .claim("email", email)
            .claim("role", role)
            .signWith(keyPair.private)
            .compact()
    }

    fun getPublicKey(): PublicKey {
        return keyPair.public
    }

    fun getPublicKeyBase64(): String {
        return Base64.getEncoder().encodeToString(keyPair.public.encoded)
    }
}
