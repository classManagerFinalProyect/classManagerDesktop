package Screens.MainAppScreen

import Screens.Course.ViewModelCourse.Companion.currentClasses
import Screens.MainAppScreen.Components.ContentState
import androidx.compose.runtime.*
import data.api.ApiServiceClass
import data.api.ApiServicePractice
import data.api.ApiServiceUser
import data.local.CompleteClass
import data.local.CompleteCourse
import data.remote.Course
import data.remote.AppUser
import data.remote.Class
import data.remote.Practice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ViewModelMainAppScreen {

    companion object {
        //Métodos get
        private var errorMessage: String by mutableStateOf ("")
        var userListResponse: List <AppUser> by mutableStateOf ( listOf ())
        var completeCourses: MutableList<CompleteCourse> = arrayListOf()
        var completeClasses: MutableList<CompleteClass> = arrayListOf()

        fun getCompleteClasses(
            composableScope: CoroutineScope,
            allClasses: MutableList<Class>,
            onFinished: () -> Unit
        ) {
            completeClasses.clear()
            var countClasses = 0

            allClasses.forEach{ classes ->
                if(classes.idPractices.size == 0) {
                    countClasses++
                    completeClasses.add(CompleteClass(classes.id,classes.name,classes.description, arrayListOf(),classes.users,classes.idOfCourse,classes.img))
                    if (countClasses == allClasses.size) {
                        onFinished()
                    }
                }

                getAllPractices(
                    classes = classes,
                    composableScope = composableScope,
                    onFinished = {
                        completeClasses.add(it)
                        countClasses++
                        if (countClasses == allClasses.size) {
                            onFinished()
                        }
                    }
                )

            }
        }

        fun getAllPractices(
            classes: Class,
            composableScope: CoroutineScope,
            onFinished: (CompleteClass) -> Unit
        ){
            var count = 0
            var currentPractices: MutableList<Practice> = arrayListOf()
            classes.idPractices.forEach{ practices ->
                composableScope.launch {
                    val apiService = ApiServicePractice.getInstance()

                    try {
                        val result = apiService.getPracticeById(practices)
                        if (result.isSuccessful) {
                            currentPractices.add(result.body()!!)
                        }
                        count++
                        if(count == classes.idPractices.size) {
                            var newCompleteClass = CompleteClass(
                                users = classes.users,
                                idOfCourse = classes.idOfCourse,
                                practices = currentPractices,
                                name = classes.name,
                                description = classes.description,
                                id = classes.id,
                                img = classes.img
                            )
                            onFinished(newCompleteClass)
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

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
                    completeCourses.add(CompleteCourse(courses.users, arrayListOf(),courses.events,courses.name,courses.description,courses.id,courses.img))
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
                                id = courses.id,
                                img = courses.img
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

        //Content State
        private val _contentState: MutableState<ContentState> = mutableStateOf(value = ContentState.COURSE)
        val contentState: State<ContentState> = _contentState


        fun updateContentState(newValue: ContentState) {
            _contentState.value = newValue
        }

        fun clearContentState() {
            _contentState.value = ContentState.COURSE
        }
    }
}