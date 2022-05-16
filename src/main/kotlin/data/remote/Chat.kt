package data.remote

import data.local.Message

data class Chat(
    val id: String,
    val conversation: MutableList<Message>
)
