package Screens.ScreenComponents.TopAppBar.CreateCourse

import Screens.ScreenComponents.TopAppBar.CreateClass.ViewModelCreateClass.Companion.newClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceClass
import data.api.ApiServiceCourse
import data.local.CurrentUser
import data.remote.Class
import data.remote.Course
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewModelCreateCourse {
    companion object {
        private var errorMessage: String by mutableStateOf ("")
        var newCourse = Course(arrayListOf(), arrayListOf(), arrayListOf(),"","","")

        fun createNewCourse(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            uploadCourse: Course
        ) {
            //Tener en cuenta q no lo puedes asignar por defecto a un curso

            composableScope.launch {
                val apiService = ApiServiceCourse.getInstance()
                try {
                    val result = apiService.postCourse(uploadCourse)
                    if (result.isSuccessful) {
                        newCourse = result.body()!!
                        CurrentUser.currentUser.courses.add(newCourse.id)
                        CurrentUser.updateCurrentUser(
                            composableScope = composableScope,
                            updateUser = CurrentUser.currentUser,
                            onFinished = {
                                CurrentUser.updateDates(
                                    composableScope = composableScope,
                                    onFinished = {
                                        onFinished()
                                    }
                                )
                            }
                        )
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }
}