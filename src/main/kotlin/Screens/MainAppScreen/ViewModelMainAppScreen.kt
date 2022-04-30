package Screens.MainAppScreen

import Screens.Course.ViewModelCourse
import Screens.Course.ViewModelCourse.Companion.currentClasses
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceClass
import data.api.ApiServiceUser
import data.local.CompleteCourse
import data.remote.Course
import data.remote.appUser
import data.remote.Class
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ViewModelMainAppScreen {

    companion object {
        //Métodos get
        private var errorMessage: String by mutableStateOf ("")
        var userListResponse: List <appUser> by mutableStateOf ( listOf ())
        var completeCourses: MutableList<CompleteCourse> = arrayListOf()

        fun getCompleteCourses(
            composableScope: CoroutineScope,
            allCourses: MutableList<Course>,
            onFinished: () -> Unit
        ) {
            completeCourses.clear()

            var countCourses = 0
            allCourses.forEach{ courses ->
                if(courses.classes.size == 0) {
                    countCourses++
                    completeCourses.add(CompleteCourse(courses.users, arrayListOf(),courses.events,courses.name,courses.description,courses.id))
                    if (countCourses == allCourses.size) {
                        onFinished()
                    }
                }
                getAllClasses(
                    courses = courses,
                    composableScope = composableScope,
                    onFinished = {
                        completeCourses.add(it)
                        countCourses++
                        if (countCourses == allCourses.size) {
                            onFinished()
                        }
                    }
                )
            }
        }

        fun getAllClasses(
            courses: Course,
            composableScope: CoroutineScope,
            onFinished: (CompleteCourse) -> Unit
        ) {
            var count = 0
            var currentClasses: MutableList<Class> = arrayListOf()

            courses.classes.forEach{ classes ->
                composableScope.launch {
                    val apiService = ApiServiceClass.getInstance()

                    try {
                        val result = apiService.getClassById(classes)
                        if (result.isSuccessful) {
                            currentClasses.add(result.body()!!)
                        }
                        count++
                        if(count == courses.classes.size) {
                            var newCompleteCourse = CompleteCourse(
                                users = courses.users,
                                classes = currentClasses,
                                events = courses.events,
                                name = courses.name,
                                description = courses.description,
                                id = courses.id
                            )
                            onFinished(newCompleteCourse)
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

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