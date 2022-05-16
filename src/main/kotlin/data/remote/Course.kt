package data.remote

import data.local.RolUser

data class Course (
    val users: MutableList<RolUser>,
    val classes: MutableList<String>,
    val events: MutableList<String>,
    var name: String,
    var description: String,
    var id: String,
    val img: String
)
