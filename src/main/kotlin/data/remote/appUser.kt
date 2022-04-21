package data.remote

data class appUser (
    val email: String,
    var imgPath: String,
    var name: String,
    val courses: MutableList<String>,
    val classes: MutableList<String>,
    val id: String,
    var description: String
)
