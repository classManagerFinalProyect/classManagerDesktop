package data.remote

import data.local.RolUser

data class Course (
    val users: MutableList<RolUser>,
    val classes: MutableList<String>,
    val events: MutableList<String>,
    val name: String,
    val description: String,
    var id: String
)
