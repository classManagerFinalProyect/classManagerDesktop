package Navigation

import Screens.Class.MainClass
import Screens.Course.MainCourse
import Screens.Login.ForgotPassword.MainForgotPassword
import Screens.Login.MainLogin
import Screens.MainAppScreen.MainAppScreen
import Screens.Register.MainRegister
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import data.remote.Course
import data.remote.Class

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationHost() {
    var screenState by remember { mutableStateOf<Screen>(Screen.Login) }
    var selectedCourse by remember { mutableStateOf(Course(arrayListOf(), arrayListOf(), arrayListOf(),"","","","")) }
    var selectedClass by remember { mutableStateOf(Class("","","", arrayListOf(), arrayListOf(),"","")) }
    var getDates by remember { mutableStateOf(true) }

    when (
        val screen = screenState
    ) {

        is Screen.Login ->
            MainLogin(
                onRegisterClick = { screenState = Screen.Register },
                onLoginClick = { screenState = Screen.MainAppScreen },
                onForgotPasswordClick = { screenState = Screen.ForgotPasswordScreen }
             )

        is Screen.Register ->
            MainRegister(
                onBack = { screenState = Screen.Login},
                onLogin = {
                    screenState = Screen.MainAppScreen
                    getDates = true
                }
            )

        is Screen.MainAppScreen ->
            MainAppScreen(
                onClickCourse = {
                    selectedCourse = it
                    screenState = Screen.CourseScreen
                    getDates = true
                },
                onClickClass = {
                    selectedClass = it
                    screenState = Screen.ClassScreen
                    getDates = true
                },
                onClickBeginning  = {
                    screenState = Screen.MainAppScreen
                    getDates = true

                },
                onCloseSession = { screenState = Screen.Login },
                getDates = getDates,
                onChangeGetDates = { getDates = it },
            )

        is Screen.CourseScreen ->
            MainCourse(
                selectedCourse = selectedCourse,
                onClickCourse = {
                    selectedCourse = it
                    screenState = Screen.CourseScreen
                    getDates = true

                },
                onClickClass = {
                    selectedClass = it
                    screenState = Screen.ClassScreen
                    getDates = true
                },
                onClickBeginning  = {
                    screenState = Screen.MainAppScreen
                    getDates = true
                },
                getDates = getDates,
                onChangeGetDates = { getDates = it },
                onCloseSession = { screenState = Screen.Login }
            )

        is Screen.ClassScreen ->
            MainClass(
                selectedClass = selectedClass,
                onClickCourse = {
                    selectedCourse = it
                    screenState = Screen.CourseScreen
                    getDates = true
                },
                onClickClass = {
                    selectedClass = it
                    screenState = Screen.ClassScreen
                    getDates = true
                },
                onClickBeginning  = {
                    screenState = Screen.MainAppScreen
                    getDates = true

                },
                onCloseSession =  { screenState = Screen.Login },
                onChangeGetDates = { getDates = it },
                getDates = getDates
            )

        is Screen.ForgotPasswordScreen ->
            MainForgotPassword(
                onLoginClick = { screenState = Screen.Login }
            )

    }
}
