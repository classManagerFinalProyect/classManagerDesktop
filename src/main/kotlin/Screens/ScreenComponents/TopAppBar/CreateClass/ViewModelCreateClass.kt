package Screens.ScreenComponents.TopAppBar.CreateClass

import Screens.Login.ViewModelLogin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceClass
import data.api.ApiServiceUser
import data.local.CurrentUser
import data.local.CurrentUser.Companion.currentUser
import data.local.CurrentUser.Companion.updateCurrentUser
import data.local.CurrentUser.Companion.updateDates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import data.remote.Class

class ViewModelCreateClass {
    companion object {
        private var errorMessage: String by mutableStateOf ("")
        var newClass = Class("","","", arrayListOf(), arrayListOf(),"")

        fun createNewClass(
            composableScope: CoroutineScope,
            onFinished: () -> Unit,
            uploadClass: Class
        ) {
            //Tener en cuenta q no lo puedes asignar por defecto a un curso

            composableScope.launch {
                val apiService = ApiServiceClass.getInstance()
                try {
                    val result = apiService.postClass(uploadClass)
                    if (result.isSuccessful) {
                        newClass = result.body()!!
                        currentUser.classes.add(newClass.id)
                        updateCurrentUser(
                            composableScope = composableScope,
                            updateUser = currentUser,
                            onFinished =  {
                                updateDates(
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