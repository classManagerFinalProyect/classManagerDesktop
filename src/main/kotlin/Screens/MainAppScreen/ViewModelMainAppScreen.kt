package Screens.MainAppScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceUser
import data.remote.Course
import data.remote.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ViewModelMainAppScreen {

    companion object {
        //Métodos get
        private var errorMessage: String by mutableStateOf ("")
        var userListResponse: List <appUser> by mutableStateOf ( listOf ())


        fun getUserList(
            composableScope: CoroutineScope,
            onFinishFunction: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceUser.getInstance()

                try {
                    val result = apiService.getUsers()
                    if (result.isSuccessful) {
                        userListResponse = result.body()!!
                        onFinishFunction()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun getNameOfCourses(
            courses: MutableList<Course>
        ): MutableList<String>{

            val result: MutableList<String> = arrayListOf()
            courses.forEach{ result.add(it.name) }

            return result
        }
    }
}