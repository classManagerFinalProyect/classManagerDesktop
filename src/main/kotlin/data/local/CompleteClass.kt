package data.local

import data.remote.Practice

data class CompleteClass(
    var id: String,
    var name: String,
    var description: String,
    val practices: MutableList<Practice>,
    val users: MutableList<RolUser>,
    val idOfCourse: String,
    val img: String
)
