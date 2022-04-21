package Screens.Login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import auth.FirebaseAuth
import data.api.ApiServiceAuthentication
import data.api.ApiServiceUser
import data.local.CurrentUser
import data.remote.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ViewModelLogin {
    companion object {


        private var errorMessage: String by mutableStateOf ("")
        var user: appUser by mutableStateOf(appUser("","","", arrayListOf(), arrayListOf(),"",""))

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
            onFinished: () -> Unit,
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
                              onFinished()
                            }
                        )
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()

    }
}