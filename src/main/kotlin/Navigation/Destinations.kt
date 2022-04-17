package Navigation

sealed class Destinations(
    val route: String
)
{
    object Login: Destinations(route = "Views.Login.Login")
}
