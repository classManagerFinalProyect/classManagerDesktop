package Screens.ScreenComponents.TopAppBar.Account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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


    }
}