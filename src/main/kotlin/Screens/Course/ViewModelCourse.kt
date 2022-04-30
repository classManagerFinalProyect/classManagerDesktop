package Screens.Course

import Screens.Course.Components.MainBody.ContentState
import Screens.Course.Components.MainBody.Members.RolState
import Screens.ScreenComponents.TopAppBar.CreateClass.ViewModelCreateClass
import Screens.ScreenComponents.TopAppBar.CreateClass.ViewModelCreateClass.Companion.newClass
import Utils.isDate
import androidx.compose.runtime.*
import data.api.ApiServiceClass
import data.api.ApiServiceCourse
import data.api.ApiServiceEvent
import data.api.ApiServiceUser
import data.local.CurrentUser
import data.local.RolUser
import data.local.UserWithRol
import data.remote.Class
import data.remote.Course
import data.remote.Event
import data.remote.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skia.impl.Log

class ViewModelCourse {
    companion object {
        private var errorMessage: String by mutableStateOf ("")
        var currentClasses: MutableList<Class> = arrayListOf()
        var currentEvents: MutableList<Event> = arrayListOf()
        var currentMembers: MutableList<UserWithRol> = arrayListOf()
        var selectedCourse : Course = Course(arrayListOf(), arrayListOf(), arrayListOf(),"","","")
        var newClass = Class("","","", arrayListOf(), arrayListOf(),"")

        var currentUser: UserWithRol = UserWithRol( appUser("","","", arrayListOf(), arrayListOf(),"",""),"")

        fun deleteEvent(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            deleteEvent: Event
        ) {
            composableScope.launch {
                val apiService = ApiServiceEvent.getInstance()
                try {
                    val result = apiService.deleteEventById(deleteEvent.id)
                    if (result.isSuccessful) {
                        selectedCourse.events.remove(deleteEvent.id)
                        currentEvents.remove(deleteEvent)
                        updateCourse(
                            updateCourse = selectedCourse,
                            composableScope = composableScope,
                            onFinished = {
                                onFinished()
                            }
                        )
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun addNewEvent(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            newEvent: Event
        ) {
            composableScope.launch {
                val apiService = ApiServiceEvent.getInstance()

                try {
                    val result = apiService.postEvent(newEvent)
                    if (result.isSuccessful) {
                        val resultEvent = result.body()!!
                        selectedCourse.events.add(resultEvent.id)
                        currentEvents.add(resultEvent)

                        updateCourse(
                            updateCourse = selectedCourse,
                            composableScope = composableScope,
                            onFinished = {
                                onFinished()
                            }
                        )
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun addNewClass(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            uploadClass: Class
        ) {
            composableScope.launch {
                val apiService = ApiServiceClass.getInstance()
                try {
                    val result = apiService.postClass(uploadClass)
                    if (result.isSuccessful) {
                        newClass = result.body()!!
                        selectedCourse.classes.add(newClass.id)
                        currentClasses.add(newClass)
                        updateCourse(
                            updateCourse = selectedCourse,
                            composableScope = composableScope,
                            onFinished = {
                                CurrentUser.currentUser.classes.add(newClass.id)

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
                        )

                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun getCurrentUser() {
            currentMembers.forEach {
                if (it.user.id == CurrentUser.currentUser.id) currentUser = it
            }
        }

        fun getCurrentClasses(
            composableScope: CoroutineScope,
            selectedCourse: Course,
            onFinished: () -> Unit
        ) {
            currentClasses.clear()
            var countTmp = 0

            if(selectedCourse.classes.size == 0)
                onFinished()

            selectedCourse.classes.forEach {
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

        fun checkIfUserIsInscribedInCourse(
            idOfUser: String
        ):Boolean {
            selectedCourse.users.forEach {
                if (it.id == idOfUser)
                    return true
            }
            return false
        }

       fun updateUser(
           updateUser: appUser,
           composableScope: CoroutineScope,
           onFinished: () -> Unit,
       ) {
           composableScope.launch {
               val apiService = ApiServiceUser.getInstance()

               try {
                   val result = apiService.putUser(updateUser)
                   if (result.isSuccessful) {
                       onFinished()
                   }
                   else {
                       Log.debug("Usuario no encontrado")
                   }
               } catch (e: Exception) {
                   errorMessage = e.message.toString()
               }
           }
       }

        fun changeRol(
            composableScope: CoroutineScope,
            newRol: String,
            appUser: appUser,
            onFinished: () -> Unit,
        ) {
            var deleteRolUser = RolUser("","")
            selectedCourse.users.forEach{
                if(it.id == appUser.id) {
                    deleteRolUser = it
                }
            }
            selectedCourse.users.remove(deleteRolUser)
            selectedCourse.users.add(RolUser(appUser.id,newRol))

            updateCourse(
                updateCourse = selectedCourse,
                composableScope = composableScope,
                onFinished = {
                    onFinished()
                }
            )
        }

        fun updateCourse(
           updateCourse: Course,
           composableScope: CoroutineScope,
           onFinished: () -> Unit,
       ) {
           composableScope.launch {
               val apiService = ApiServiceCourse.getInstance()

               try {
                   val result = apiService.putCourse(updateCourse)
                   if (result.isSuccessful) {
                       onFinished()
                   }
                   else {
                       Log.debug("Usuario no encontrado")
                   }
               } catch (e: Exception) {
                   errorMessage = e.message.toString()
               }
           }
       }

        fun addNewMember(
            idOfUser: String,
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            rol: String
        ) {
            if(!checkIfUserIsInscribedInCourse(idOfUser = idOfUser)) {

                composableScope.launch {
                    val apiService = ApiServiceUser.getInstance()

                    try {
                        val result = apiService.getUserById(idOfUser)
                        if (result.isSuccessful) {
                            val newUser = result.body()!!
                            newUser.courses.add(selectedCourse.id)

                            updateUser(
                                composableScope = composableScope,
                                updateUser = newUser,
                                onFinished =  {
                                    selectedCourse.users.add(
                                        RolUser(
                                            id  = idOfUser,
                                            rol = rol
                                        )
                                    )
                                    updateCourse(
                                        composableScope = composableScope,
                                        updateCourse = selectedCourse,
                                        onFinished  = {
                                            currentMembers.add(UserWithRol(newUser, rol))
                                            onFinished()
                                        }
                                    )
                                }
                            )
                        }
                        else {
                            Log.debug("Usuario no encontrado")
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }

        }

        fun updateEvent(
            composableScope: CoroutineScope,
            event: Event,
            onFinished: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceEvent.getInstance()

                try {
                    val result = apiService.updateEvent(event)
                    if (result.isSuccessful) {
                        currentEvents.add(result.body()!!)
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
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

        fun deleteUserToCurrentCourse(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            user: UserWithRol
        ) {
            currentMembers.remove(user)


            var deleteRolUser: RolUser = RolUser("","")
            selectedCourse.users.forEach { if (it.id == user.user.id) deleteRolUser = it }
            selectedCourse.users.remove(deleteRolUser)

            updateCourse(
                composableScope = composableScope,
                updateCourse = selectedCourse,
                onFinished = {
                    user.user.courses.remove(selectedCourse.id)
                    updateUser(
                        composableScope = composableScope,
                        updateUser = user.user,
                        onFinished = {
                            onFinished()
                        }
                    )
                }
            )
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
                            currentMembers.add(UserWithRol(result.body()!!, it.rol))
                        }
                        countTmp++
                        if(countTmp == selectedCourse.users.size) {
                            getCurrentUser()
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

        //Rol State
        private val _rolState: MutableState<RolState> = mutableStateOf(value = RolState.ALL)
        val rolState: State<RolState> = _rolState


        fun updateRolState(newValue: RolState) {
            _rolState.value = newValue
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
