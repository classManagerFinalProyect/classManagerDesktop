package Screens.Login

import Utils.createSha256
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceAuthentication
import data.api.ApiServiceUser
import data.local.CurrentUser
import data.local.NewUser
import data.remote.AppUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewModelLogin {
    companion object {


        private var errorMessage: String by mutableStateOf ("")
        var user: AppUser by mutableStateOf(AppUser("","","", arrayListOf(), arrayListOf(),"","",""))
        var allUsers: List<AppUser> = arrayListOf()

        fun login(
            composableScope: CoroutineScope,
            email: String,
            password: String,
            onFinished: (Boolean) -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceAuthentication.getInstance()
                try {
                    val newPassword = createSha256(password)!!
                    val result = apiService.login(NewUser("", email, password ,newPassword))
                    if (result.isSuccessful) {
                        user = result.body()!!
                        CurrentUser.currentUser = user
                        CurrentUser.updateDates(
                            composableScope = composableScope,
                            onFinished = {
                                onFinished(true)
                            }
                        )
                    }
                    else {
                        onFinished(false)
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
        fun getUsers(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceUser.getInstance()
                try {
                    val result = apiService.getUsers()
                    if (result.isSuccessful) {
                        allUsers =  result.body()!!
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun sendEmailToChangePassword(
            composableScope: CoroutineScope,
            email: String,
            onFinished: () -> Unit
        ) {


            composableScope.launch {
                val apiService = ApiServiceAuthentication.getInstance()
                try {
                    val result = apiService.updatePasswordByEmail(email)
                    if (result.isSuccessful) {
                        onFinished()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
/*
        fun isValidUser(
            email: String,
            password: String
        ): Boolean {
            val passwordSha = getSHA256(password)

            allUsers.forEach {
                if (it.email == email && it.password == passwordSha) {
                    user = it
                    return true
                }
            }
            return false
        }

        fun getCurrentUser(
            composableScope: CoroutineScope,
            onFinished: (Boolean) -> Unit,
            idOfUser: String
        ) {

            composableScope.launch {
                val apiService = ApiServiceUser.getInstance()
                try {
                    val result = apiService.getUserById(idOfUser)
                    if (result.isSuccessful) {
                        user = result.body()!!
                        CurrentUser.currentUser = user
                        CurrentUser.updateDates(
                            composableScope = composableScope,
                            onFinished = {
                              onFinished(true)
                            }
                        )
                    }
                    else {
                        onFinished(false)
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
        */
    }
}