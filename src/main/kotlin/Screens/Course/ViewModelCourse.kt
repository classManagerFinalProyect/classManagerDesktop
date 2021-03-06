package Screens.Course

import Screens.Course.Components.MainBody.ContentState
import Screens.Course.Components.MainBody.Members.RolState
import androidx.compose.runtime.*
import data.api.*
import data.local.CurrentUser
import data.local.RolUser
import data.local.UserWithRol
import data.remote.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.skia.impl.Log

class ViewModelCourse {
    companion object {
        private var errorMessage: String by mutableStateOf ("")
        var currentClasses: MutableList<Class> = arrayListOf()
        var currentEvents: MutableList<Event> = arrayListOf()
        var currentMembers: MutableList<UserWithRol> = arrayListOf()
        var selectedCourse : Course = Course(arrayListOf(), arrayListOf(), arrayListOf(),"","","","")
        private var newClass = Class("","","", arrayListOf(), arrayListOf(),"","")

        var currentUser: UserWithRol = UserWithRol( AppUser("","","", arrayListOf(), arrayListOf(),"","",""),"")

        fun leaveCourse(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            currentClasses.forEach {

                it.users.remove(RolUser(currentUser.user.id, currentUser.rol))
                updateClass(
                    composableScope = composableScope,
                    updateClass = it,
                    onFinished = {

                    }
                )
            }

            selectedCourse.users.remove(RolUser(currentUser.user.id, currentUser.rol))
            updateCourse(
                composableScope = composableScope,
                updateCourse = selectedCourse,
                onFinished = {

                }
            )
            CurrentUser.currentUser.courses.remove(selectedCourse.id)
            currentClasses.forEach{
                CurrentUser.currentUser.classes.remove(it.id)
            }

            CurrentUser.updateCurrentUser(
                composableScope = composableScope,
                updateUser = CurrentUser.currentUser,
                onFinished = {
                    CurrentUser.updateDates(
                        onFinished = {
                            onFinished()
                        },
                        composableScope = composableScope
                    )
                }
            )

        }
        fun updateCurrentCourse(
            composableScope: CoroutineScope,
            newName: String,
            newDescription: String,
            onFinished: () -> Unit
        ){
            selectedCourse.name = newName
            selectedCourse.description = newDescription
            updateCourse(
                composableScope = composableScope,
                updateCourse = selectedCourse,
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

        fun deleteCurrentCourse(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ){
            currentMembers.forEach{
                it.user.courses.remove(selectedCourse.id)
                currentClasses.forEach{ currentClass ->
                    it.user.classes.remove(currentClass.id)
                    CurrentUser.currentUser.classes.remove(currentClass.id)
                    currentClass.idPractices.forEach{ idPractice ->

                        getPracticeById(
                            composableScope = composableScope,
                            idOfPractice = idPractice,
                            onFinished = { practice ->

                                deletePractice(
                                    composableScope = composableScope,
                                    practice = practice,
                                )
                            }
                        )

                    }

                    deleteClass(
                        composableScope = composableScope,
                        idOfClass = currentClass.id,
                        onFinished = {

                        }
                    )
                }

                updateUser(
                    composableScope = composableScope,
                    updateUser = it.user,
                    onFinished = {

                    }
                )
            }


            deleteCourse(
                composableScope = composableScope,
                idOfCourse = selectedCourse.id,
                onFinished = {
                   CurrentUser.currentUser.courses.remove(selectedCourse.id)
                    CurrentUser.updateDates(
                        composableScope = composableScope,
                        onFinished = {
                            onFinished()
                        }
                    )
                }
            )
        }

        private fun getPracticeById(
            composableScope: CoroutineScope,
            idOfPractice: String,
            onFinished: (Practice) -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServicePractice.getInstance()
                try {
                    val result = apiService.getPracticeById(idOfPractice)
                    if (result.isSuccessful) {
                        onFinished(result.body()!!)
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        private fun deletePractice(
            composableScope: CoroutineScope,
            practice: Practice,
        ) {


            deleteChatById(
                composableScope = composableScope,
                idOfChat = practice.idOfChat,
                onFinished = {}
            )

            deletePracticeById(
                composableScope = composableScope,
                idOfPractice = practice.id,
                onFinished = {
                }
            )
        }

        private fun deleteChatById(
            composableScope: CoroutineScope,
            idOfChat: String,
            onFinished: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceChat.getInstance()

                try {
                    val result = apiService.deleteChatById(idOfChat)
                    if (result.isSuccessful) {
                        onFinished()
                    }
                    else {
                        Log.debug("Error trying delete chat")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        private fun deletePracticeById(
            composableScope: CoroutineScope,
            idOfPractice: String,
            onFinished: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServicePractice.getInstance()

                try {
                    val result = apiService.deletePracticeById(idOfPractice)
                    if (result.isSuccessful) {
                        onFinished()
                    }
                    else {
                        Log.debug("Error trying delete chat")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        private fun deleteClass(
            composableScope: CoroutineScope,
            idOfClass: String,
            onFinished: () -> Unit
        ){
            composableScope.launch {
                val apiService = ApiServiceClass.getInstance()
                try {
                    val result = apiService.deleteClassById(idOfClass)
                    if (result.isSuccessful) {
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        private fun deleteCourse(
            composableScope: CoroutineScope,
            idOfCourse: String,
            onFinished: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceCourse.getInstance()
                try {
                    val result = apiService.deleteCourseById(idOfCourse)
                    if (result.isSuccessful) {
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

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

        private fun getCurrentUser() {
            selectedCourse.users.forEach {
                if (it.id == CurrentUser.currentUser.id) currentUser = UserWithRol(CurrentUser.currentUser,it.rol)
            }
        }

        fun getCurrentClasses(
            composableScope: CoroutineScope,
            selectedCourse: Course,
            onFinished: () -> Unit
        ) {
           getCurrentUser()

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

        private fun checkIfUserIsInscribedInCourse(
            idOfUser: String
        ):Boolean {
            selectedCourse.users.forEach {
                if (it.id == idOfUser)
                    return true
            }
            return false
        }

        private fun updateUser(
           updateUser: AppUser,
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
            appUser: AppUser,
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
            changeRolInCurrentClasses(
                composableScope = composableScope,
                newRolUser =  newRol,
                appUser = appUser,
                onFinished = {

                }
            )

            updateCourse(
                updateCourse = selectedCourse,
                composableScope = composableScope,
                onFinished = {
                    onFinished()
                }
            )
        }

        private fun changeRolInCurrentClasses(
            composableScope: CoroutineScope,
            newRolUser: String,
            appUser: AppUser,
            onFinished: () -> Unit
        ) {
            var deleteRolUser = RolUser("","")

            currentClasses.forEach {
                it.users.forEach{ rolUser ->
                    if(rolUser.id  == appUser.id) {
                        deleteRolUser = rolUser
                    }
                }
                it.users.remove(deleteRolUser)
                it.users.add(RolUser(appUser.id, newRolUser))
                updateClass(
                    composableScope = composableScope,
                    updateClass = it,
                    onFinished = {
                        onFinished()
                    }
                )
            }
        }


        private fun updateCourse(
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
                            
                            currentClasses.forEach { newClass ->
                                newUser.classes.add(newClass.id)
                            }

                            updateUser(
                                composableScope = composableScope,
                                updateUser = newUser,
                                onFinished =  {
                                    val newRolUser = RolUser(
                                        id  = idOfUser,
                                        rol = rol
                                    )

                                    selectedCourse.users.add(newRolUser)
                                    addNewMemberInCLasses(
                                        composableScope = composableScope,
                                        rolUser = newRolUser ,
                                        onFinished = {

                                        }
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

        private fun addNewMemberInCLasses(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            rolUser: RolUser
        ){
            currentClasses.forEach{
                it.users.remove(rolUser)
                it.users.add(rolUser)

                updateClass(
                    composableScope = composableScope,
                    updateClass = it,
                    onFinished = {
                        onFinished()
                    }
                )
            }

        }
        private fun updateClass(
            composableScope: CoroutineScope,
            updateClass: Class,
            onFinished: () -> Unit,
        ) {
            composableScope.launch {
                val apiService = ApiServiceClass.getInstance()

                try {
                    val result = apiService.putClass(updateClass)
                    if (result.isSuccessful) {
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
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


            var deleteRolUser = RolUser("","")
            selectedCourse.users.forEach { if (it.id == user.user.id) deleteRolUser = it }
            selectedCourse.users.remove(deleteRolUser)

            deleteUserInCurrentClasses(
                composableScope = composableScope,
                userRol = deleteRolUser,
                onFinished = {

                }
            )
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

        private fun deleteUserInCurrentClasses(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            userRol: RolUser
        ){
            currentClasses.forEach{
                it.users.remove(userRol)

                updateClass(
                    composableScope = composableScope,
                    updateClass = it,
                    onFinished = {
                       onFinished()
                    }
                )
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
                            currentMembers.add(UserWithRol(result.body()!!, it.rol))
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

        //Rol State
        private val rolState_: MutableState<RolState> = mutableStateOf(value = RolState.ALL)

        fun updateRolState(newValue: RolState) {
            rolState_.value = newValue
        }

        //Content State
        private val contentState_: MutableState<ContentState> = mutableStateOf(value = ContentState.CLASSES)
        val contentState: State<ContentState> = contentState_

        fun updateContentState(newValue: ContentState) {
            contentState_.value = newValue
        }

    }
}
