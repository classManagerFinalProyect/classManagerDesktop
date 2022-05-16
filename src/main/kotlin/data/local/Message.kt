package data.local

import data.remote.AppUser

data class Message(
    val message: String,
    val sentBy: AppUser,
    val sentOn: String
)

