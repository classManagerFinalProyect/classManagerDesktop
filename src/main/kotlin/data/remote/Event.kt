package data.remote

data class Event(
    var id: String,
    val idOfCourse: String,
    val name: String,
    val nameOfClass: String,
    val finalTime: String,
    val initialTime: String,
    val date: String
)
