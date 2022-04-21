package Screens.ScreenComponents.TopAppBar.Account

import Screens.Course.ViewModelCourse
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceAuthentication
import data.api.ApiServiceEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ViewModelAccount {
    companion object {
        private var errorMessage: String by mutableStateOf ("")

        fun deleteAccount(
            composableScope: CoroutineScope,
            uid: String,
            onFinish: () -> Unit
        ) {
            composableScope.launch {
                val apiService = ApiServiceAuthentication.getInstance()

                try {
                    val result = apiService.deleteAccount(uid)
                    if (result.isSuccessful) {
                        onFinish()
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

        //Validaciones
        fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()

    }
}