package Screens.Login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceAuthentication
import data.api.ApiServiceUser
import data.local.CurrentUser
import data.remote.AppUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ViewModelLogin {
    companion object {


        private var errorMessage: String by mutableStateOf ("")
        var user: AppUser by mutableStateOf(AppUser("","","", arrayListOf(), arrayListOf(),"",""))

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

        fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()

    }
}