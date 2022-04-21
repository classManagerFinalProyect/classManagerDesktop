package data.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceClass
import data.api.ApiServiceCourse
import data.remote.Class
import data.remote.Course
import data.remote.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CurrentUser {

    companion object {
        private var errorMessage: String by mutableStateOf ("")

        var myCourses: MutableList<Course> = mutableListOf()
        val myClasses: MutableList<Class> = mutableListOf()
        var currentUser: appUser = appUser("","","", mutableListOf<String>(), arrayListOf<String>(),"","")



        fun getMyCourses(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            myCourses.clear()
            var countTmp = 0
            var firstAcces = true
            if(currentUser.courses.size == 0) onFinished()
            currentUser.courses.forEach {
                composableScope.launch {
                    val apiService = ApiServiceCourse.getInstance()
                    try {
                        val result = apiService.getCourserById(it)
                        if (result.isSuccessful) {
                            myCourses.add(result.body()!!)
                            if (firstAcces) {
                                firstAcces = false
                            }
                        }
                        countTmp++

                        if(countTmp.equals(currentUser.courses.size)) {
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
            var firstAcces = true
            if(currentUser.classes.size == 0) onFinished()
            currentUser.classes.forEach{
                composableScope.launch {
                     val apiService = ApiServiceClass.getInstance()

                    try {
                        val result = apiService.getClassById(it)
                        if (result.isSuccessful) {
                            myClasses.add(result.body()!!)
                            if(firstAcces) {
                                firstAcces = false
                                onFinished()
                            }
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