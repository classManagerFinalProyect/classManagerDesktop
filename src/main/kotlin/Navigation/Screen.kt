package Navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Screen : Parcelable{

    @Parcelize
    object Login : Screen()

    @Parcelize
    object Register : Screen()

    @Parcelize
    object MainAppScreen : Screen()

    @Parcelize
    object CourseScreen: Screen()

    @Parcelize
    object ClassScreen: Screen()

    @Parcelize
    object ForgotPasswordScreen: Screen()

}