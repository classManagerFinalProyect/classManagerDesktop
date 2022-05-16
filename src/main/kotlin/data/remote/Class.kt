package data.remote

import data.local.RolUser

data class Class (
    var id: String,
    var name: String,
    var description: String,
    val idPractices: MutableList<String>,
    val users: MutableList<RolUser>,
    val idOfCourse: String,
    val img: String
)
