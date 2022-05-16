package data.local
import data.remote.Class

data class CompleteCourse(
    val users: MutableList<RolUser>,
    val classes: MutableList<Class>,
    val events: MutableList<String>,
    val name: String,
    val description: String,
    var id: String,
    val img: String
)
