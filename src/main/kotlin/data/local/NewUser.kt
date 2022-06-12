package data.local

data class NewUser(
    val id: String,
    val email: String,
    val password: String,
    val passwordSha256: String
)
