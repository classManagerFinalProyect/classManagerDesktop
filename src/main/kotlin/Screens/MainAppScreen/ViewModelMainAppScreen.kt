package Screens.MainAppScreen

import Screens.MainAppScreen.Components.ContentState
import androidx.compose.runtime.*
import data.api.ApiServiceClass
import data.api.ApiServicePractice
import data.local.CompleteClass
import data.local.CompleteCourse
import data.remote.Course
import data.remote.Class
import data.remote.Practice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ViewModelMainAppScreen {

    companion object {
        //Métodos get
        private var errorMessage: String by mutableStateOf ("")
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

        private fun getAllPractices(
            classes: Class,
            composableScope: CoroutineScope,
            onFinished: (CompleteClass) -> Unit
        ){
            var count = 0
            val currentPractices: MutableList<Practice> = arrayListOf()
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
                            val newCompleteClass = CompleteClass(
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

            if(allCourses.size == 0) onFinished()
            allCourses.forEach { courses ->
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

       private fun getAllClasses(
            courses: Course,
            composableScope: CoroutineScope,
            onFinished: (CompleteCourse) -> Unit
        ) {
            var count = 0
            val currentClasses: MutableList<Class> = arrayListOf()

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
                            val newCompleteCourse = CompleteCourse(
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

        //Content State
        private val _contentState: MutableState<ContentState> = mutableStateOf(value = ContentState.COURSE)
        val contentState: State<ContentState> = _contentState


        fun updateContentState(newValue: ContentState) {
            _contentState.value = newValue
        }

    }
}