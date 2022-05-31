package Screens.ScreenComponents.TopAppBar.Profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServiceUser
import data.remote.AppUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewModelProfile {
    companion object{
        private var errorMessage: String by mutableStateOf ("")
        private var postUser = AppUser("","","", arrayListOf(), arrayListOf(),"","","")

        fun saveChange(
            newUser: AppUser,
            composableScope: CoroutineScope,
            onFinish: () -> Unit,
        ) {
            composableScope.launch {
                val apiService = ApiServiceUser.getInstance()

                try {
                    val result = apiService.putUser(newUser)
                    if (result.isSuccessful) {
                        postUser = result.body()!!
                        onFinish()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

    }
}