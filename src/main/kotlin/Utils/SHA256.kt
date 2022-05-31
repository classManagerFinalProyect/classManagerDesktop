package Utils

import akka.stream.impl.fusing.Log
import java.security.MessageDigest

fun getSHA256(passw : String) : String {
    try{
        val bytes = passw.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }

    }catch (e: Exception){
        throw e
    }
}

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

