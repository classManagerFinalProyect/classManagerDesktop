package Screens.Course

import Screens.Course.Components.MainBody.ContentState
import androidx.compose.runtime.*
import data.api.ApiServiceClass
import data.api.ApiServiceEvent
import data.api.ApiServiceUser
import data.local.CurrentUser
import data.local.RolUser
import data.remote.Class
import data.remote.Course
import data.remote.Event
import data.remote.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewModelCourse {
    companion object {
        private var errorMessage: String by mutableStateOf ("")
        var currentClasses: MutableList<Class> = arrayListOf()
        var currentEvents: MutableList<Event> = arrayListOf()
        var currentMembers: MutableList<appUser> = arrayListOf()

        fun getCurrentClasses(
            composableScope: CoroutineScope,
            selectedCourse: Course,
            onFinished: () -> Unit
        ) {
            currentClasses.clear()
            var countTmp = 0

            if(selectedCourse.classes.size == 0) onFinished()
            selectedCourse.classes.forEach{
                composableScope.launch {
                    val apiService = ApiServiceClass.getInstance()

                    try {
                        val result = apiService.getClassById(it)
                        if (result.isSuccessful) {
                            currentClasses.add(result.body()!!)
                        }
                        countTmp++
                        if(countTmp == selectedCourse.classes.size) {
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

        fun getCurrentEvents(
            composableScope: CoroutineScope,
            selectedCourse: Course,
            onFinished: () -> Unit
        ) {
            currentEvents.clear()
            var countTmp = 0


            if(selectedCourse.events.size == 0) onFinished()
            selectedCourse.events.forEach{
                composableScope.launch {
                    val apiService = ApiServiceEvent.getInstance()

                    try {
                        val result = apiService.getEventById(it)
                        if (result.isSuccessful) {
                            currentEvents.add(result.body()!!)
                        }
                        countTmp++
                        if(countTmp == selectedCourse.events.size) {
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

        fun getCurrentMembers(
            composableScope: CoroutineScope,
            selectedCourse: Course,
            onFinished: () -> Unit
        ) {
            currentMembers.clear()
            var countTmp = 0


            if(selectedCourse.users.size == 0) onFinished()
            selectedCourse.users.forEach{
                composableScope.launch {
                    val apiService = ApiServiceUser.getInstance()

                    try {
                        val result = apiService.getUserById(it.id)
                        if (result.isSuccessful) {
                            currentMembers.add(result.body()!!)
                        }
                        countTmp++
                        if(countTmp == selectedCourse.users.size) {
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }


        //Content State
        private val _contentState: MutableState<ContentState> = mutableStateOf(value = ContentState.CLASSES)
        val contentState: State<ContentState> = _contentState


        fun updateContentState(newValue: ContentState) {
            _contentState.value = newValue
        }

        fun clearContentState() {
            _contentState.value = ContentState.CLASSES
        }
    }

}
