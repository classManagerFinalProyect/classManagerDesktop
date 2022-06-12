package Utils

import java.security.MessageDigest

fun createSha256(base: String): String? {
    return try {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(base.toByteArray(charset("UTF-8")))
        val hexString = StringBuilder()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        hexString.toString()
    } catch (ex: java.lang.Exception) {
        throw RuntimeException(ex)
    }
}

