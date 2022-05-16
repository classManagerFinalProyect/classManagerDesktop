package data.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceClass
import data.api.ApiServiceCourse
import data.api.ApiServiceUser
import data.remote.Class
import data.remote.Course
import data.remote.AppUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CurrentUser {

    companion object {
        private var errorMessage: String by mutableStateOf ("")

        var myCourses: MutableList<Course> = mutableListOf()
        val myClasses: MutableList<Class> = mutableListOf()
        var currentUser: AppUser = AppUser("","","", mutableListOf<String>(), arrayListOf<String>(),"","")

        fun updateCurrentUser(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            updateUser: AppUser
        ) {
            composableScope.launch {
                val apiService = ApiServiceUser.getInstance()
                try {
                    val result = apiService.putUser(updateUser)
                    if (result.isSuccessful) {
                        currentUser = result.body()!!
                        onFinished()
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }


        fun getMyCourses(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            myCourses.clear()
            var count = 0

            if(currentUser.courses.size == 0) onFinished()

            currentUser.courses.forEach {
                composableScope.launch {
                    val apiService = ApiServiceCourse.getInstance()
                    try {
                        val result = apiService.getCourserById(it)
                        if (result.isSuccessful) {
                            myCourses.add(result.body()!!)
                        }
                        count++

                        if(count == currentUser.courses.size) {
                            onFinished()
                        }

                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }

                //To do
                /*
                val apiService = ApiServiceCourse.getInstance()

                try {
                    val result = apiService.getCourserByListOfIds(currentUser.courses)
                    if (result.isSuccessful) {
                        myCourses = result.body()!!
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }

                */
            }
        }

        fun getMyClasses(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            myClasses.clear()
            var count = 0

            if(currentUser.classes.size == 0) onFinished()

            currentUser.classes.forEach{
                composableScope.launch {
                     val apiService = ApiServiceClass.getInstance()

                    try {
                        val result = apiService.getClassById(it)
                        if (result.isSuccessful) {
                            myClasses.add(result.body()!!)
                        }
                        count++
                        if(count == currentUser.classes.size) {
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }
        fun updateDates(
            onFinished:  () -> Unit,
            composableScope: CoroutineScope
        ) {
            getMyClasses(
                composableScope = composableScope,
                onFinished = {
                    getMyCourses(
                        composableScope = composableScope,
                        onFinished = {
                            onFinished()
                        }
                    )
                }
            )
        }

    }
}