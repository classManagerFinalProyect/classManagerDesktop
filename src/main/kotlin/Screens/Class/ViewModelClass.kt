package Screens.Class

import Screens.Class.Components.MainBody.ContentState
import Screens.Class.Components.MainBody.Members.RolState
import Screens.Course.ViewModelCourse
import androidx.compose.runtime.*
import data.api.*
import data.local.CompletePractice
import data.local.CurrentUser
import data.local.RolUser
import data.local.UserWithRol
import data.remote.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.skia.impl.Log

class ViewModelClass {
    companion object{
        private var errorMessage: String by mutableStateOf ("")
        var currentMembers: MutableList<UserWithRol> = arrayListOf()
        var selectedClass : Class = Class("","","", arrayListOf(), arrayListOf(),"","")
        var currentPractices : MutableList<CompletePractice> = arrayListOf()
        var currentUser: UserWithRol = UserWithRol( AppUser("","","", arrayListOf(), arrayListOf(),"",""),"")
        var currentCourse: Course = Course(arrayListOf(), arrayListOf(), arrayListOf(),"","","","")



        fun deleteCurrentClass(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ){
            currentCourse.classes.remove(selectedClass.id)

            updateCourse(
                composableScope = composableScope,
                updateCourse = currentCourse,
                onFinished = {
                    deleteClass(
                        composableScope = composableScope,
                        idOfClass = selectedClass.id,
                        onFinished = {
                            CurrentUser.myClasses.remove(selectedClass)
                            onFinished()
                        }
                    )
                }
            )
            currentMembers.forEach {
                it.user.classes.remove(selectedClass.id)
                updateUser(
                    composableScope = composableScope,
                    updateUser = it.user,
                    onFinished = {

                    }
                )
            }



        }

        fun deleteClass(
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
                    } else {
                        Log.debug("Error trying delete class")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun updateCurrentClass(
            composableScope: CoroutineScope,
            newName: String,
            newDescription: String,
            onFinished: () -> Unit
        ){
            selectedClass.name = newName
            selectedClass.description = newDescription

            updateClass(
                composableScope = composableScope,
                updateClass = selectedClass,
                onFinished = {
                    onFinished()
                }
            )
        }

        fun changeRol(
            composableScope: CoroutineScope,
            newRol: String,
            appUser: AppUser,
            onFinished: () -> Unit,
        ) {
            var deleteRolUser = RolUser("","")
            selectedClass.users.forEach{
                if(it.id == appUser.id) {
                    deleteRolUser = it
                }
            }
            selectedClass.users.remove(deleteRolUser)
            selectedClass.users.add(RolUser(appUser.id,newRol))

            updateClass(
                composableScope = composableScope,
                updateClass = selectedClass,
                onFinished = {
                    onFinished()
                }
            )
        }

        fun checkIfUserIsInscribedInClass(
            idOfUser: String
        ):Boolean {
            selectedClass.users.forEach {
                if (it.id == idOfUser)
                    return true
            }
            return false
        }

        fun addNewMember(
            idOfUser: String,
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            rol: String
        ) {
            if(!checkIfUserIsInscribedInClass(idOfUser = idOfUser)) {

                composableScope.launch {
                    val apiService = ApiServiceUser.getInstance()

                    try {
                        val result = apiService.getUserById(idOfUser)
                        if (result.isSuccessful) {
                            val newUser = result.body()!!
                            newUser.classes.add(selectedClass.id)

                            updateUser(
                                composableScope = composableScope,
                                updateUser = newUser,
                                onFinished = {
                                    val newRolUser = RolUser(
                                        id = idOfUser,
                                        rol = rol
                                    )

                                    selectedClass.users.add(newRolUser)
                                    updateClass(
                                        composableScope = composableScope,
                                        updateClass = selectedClass,
                                        onFinished =  {
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

        fun getCurrentCourse(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceCourse.getInstance()

                try {
                    val result = apiService.getCourserById(selectedClass.idOfCourse)
                    if (result.isSuccessful) {
                        val getCourse = result.body()!!
                        currentCourse = getCourse
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
        fun getCurrentMembers(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            currentMembers.clear()
            var countTmp = 0


            if(selectedClass.users.size == 0) onFinished()
            selectedClass.users.forEach{
                composableScope.launch {
                    val apiService = ApiServiceUser.getInstance()

                    try {
                        val result = apiService.getUserById(it.id)
                        if (result.isSuccessful) {
                            currentMembers.add(UserWithRol(result.body()!!, it.rol))
                        }
                        countTmp++
                        if(countTmp == selectedClass.users.size) {
                            getCurrentUser()
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

        private fun getCurrentUser() {
            currentMembers.forEach {
                if (it.user.id == CurrentUser.currentUser.id) currentUser = it
            }
        }

        fun deleteUserToCurrentClass(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            user: UserWithRol
        ) {
            currentMembers.remove(user)


            var deleteRolUser: RolUser = RolUser("","")
            selectedClass.users.forEach { if (it.id == user.user.id) deleteRolUser = it }
            selectedClass.users.remove(deleteRolUser)



            updateClass(
                composableScope = composableScope,
                updateClass = selectedClass,
                onFinished = {
                    user.user.classes.remove(selectedClass.id)
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

        fun deleteUserInCurrentCourse(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            updateCourse: Course
        ){
            updateCourse(
                updateCourse = updateCourse,
                composableScope =composableScope,
                onFinished = {
                    onFinished()
                }
            )
        }

        fun updateUser(
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

        fun updateChat(
            composableScope: CoroutineScope,
            chat: Chat,
            onFinished: () -> Unit
        ){
            composableScope.launch {
                val apiService = ApiServiceChat.getInstance()

                try {
                    val result = apiService.putChat(chat)
                    if (result.isSuccessful) {
                        val chat  = result.body()!!
                        onFinished()
                    }
                    else {
                        Log.debug("Error trying update chat")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun getChatById(
            composableScope: CoroutineScope,
            idOfChat: String,
            onFinished: (Chat) -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceChat.getInstance()

                try {
                    val result = apiService.getChatById(idOfChat)
                    if (result.isSuccessful) {
                        val chat  = result.body()!!
                        onFinished(chat)
                    }
                    else {
                        Log.debug("Error trying delete chat")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun deletePractice(
            composableScope: CoroutineScope,
            practice: Practice,
            onFinished: () -> Unit
        ) {


            deletePracticeById(
                composableScope = composableScope,
                idOfPractice = practice.id,
                onFinished = {
                    selectedClass.idPractices.remove(practice.id)
                    getChatById(
                        composableScope = composableScope,
                        idOfChat = practice.idOfChat,
                        onFinished = { chat ->
                            deleteChatById(
                                composableScope = composableScope,
                                idOfChat = practice.idOfChat,
                                onFinished = {}
                            )
                            currentPractices.remove(CompletePractice(chat = chat, practice = practice))
                            updateClass(
                                composableScope = composableScope,
                                updateClass = selectedClass,
                                onFinished = {
                                    onFinished()
                                }
                            )
                        }
                    )
                }
            )
        }

        fun deletePracticeById(
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

        fun deleteChatById(
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

        fun addPractice(
            composableScope: CoroutineScope,
            practice: Practice,
            onFinished: () -> Unit
        ) {
            createPracticeChat(
                composableScope = composableScope,
                onFinished = { chat ->
                    practice.idOfChat = chat.id
                    createPractice(
                        composableScope = composableScope,
                        practice = practice,
                        onFinished = {
                            CurrentUser.myClasses.remove(selectedClass)
                            selectedClass.idPractices.add(it.id)
                            CurrentUser.myClasses.add(selectedClass)

                            getChatById(
                                composableScope = composableScope,
                                idOfChat = it.idOfChat,
                                onFinished = { chat ->

                                    currentPractices.add(CompletePractice(practice = it, chat = chat))
                                    updateClass(
                                        composableScope = composableScope,
                                        updateClass = selectedClass,
                                        onFinished = {
                                            onFinished()
                                        }
                                    )

                                }
                            )
                        }
                    )
                }
            )
        }

        fun createPractice(
            composableScope: CoroutineScope,
            onFinished: (Practice) -> Unit,
            practice: Practice
        ) {
            composableScope.launch {

                val apiService = ApiServicePractice.getInstance()

                try {
                    val result = apiService.postPractice(practice)
                    if (result.isSuccessful) {

                        onFinished(result.body()!!)
                    } else {
                        Log.debug("Error trying upload practice")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun updateClass(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            updateClass: Class
        ) {
            composableScope.launch {

                val apiService = ApiServiceClass.getInstance()

                try {
                    val result = apiService.putClass(updateClass)
                    if (result.isSuccessful) {
                        onFinished()
                    } else {
                        Log.debug("Error trying upload practice")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun createPracticeChat(
            composableScope: CoroutineScope,
            onFinished: (Chat) -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceChat.getInstance()

                val newChat = Chat("", arrayListOf())
                try {
                    val result = apiService.postChat(newChat)
                    if (result.isSuccessful) {
                        onFinished(result.body()!!)
                    }
                    else {
                        Log.debug("Error trying upload chat")
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }


        fun getCurrentPractices(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            currentPractices.clear()
            var countTmp = 0


            if(selectedClass.idPractices.size == 0) onFinished()
            selectedClass.idPractices.forEach{
                composableScope.launch {
                    val apiService = ApiServicePractice.getInstance()

                    try {
                        val result = apiService.getPracticeById(it)
                        if (result.isSuccessful) {
                            val practice = result.body()!!
                            getChatById(
                                composableScope = composableScope,
                                idOfChat = practice.idOfChat,
                                onFinished = { chat ->
                                    currentPractices.add(CompletePractice(practice = practice, chat = chat))
                                    countTmp++
                                    if(countTmp == selectedClass.idPractices.size) {
                                        onFinished()
                                    }
                                }
                            )
                        } else {
                            countTmp++
                        }
                        if(countTmp == selectedClass.idPractices.size) {
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


        fun updateRolState(newValue: Screens.Class.Components.MainBody.Members.RolState) {
            _rolState.value = newValue
        }

        //Content State
        private val _contentState: MutableState<ContentState> = mutableStateOf(value = ContentState.PRACTICES)
        val contentState: State<ContentState> = _contentState


        fun updateContentState(newValue: ContentState) {
            _contentState.value = newValue
        }

    }
}