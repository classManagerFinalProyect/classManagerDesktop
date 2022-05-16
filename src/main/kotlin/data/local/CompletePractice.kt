package data.local

import data.remote.Chat
import data.remote.Practice

data class CompletePractice(
    val practice: Practice,
    val chat: Chat
)
