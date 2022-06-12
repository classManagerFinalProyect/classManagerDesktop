package Screens.Register


import Utils.isValidEmail
import Utils.isValidPassword
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

class ViewModelRegister {
    companion object {
        private var errorMessage: String by mutableStateOf ("")
        var createUser = NewUser("","","","")
        var user: AppUser by mutableStateOf(AppUser("","","", arrayListOf(), arrayListOf(),"","",""))

        fun createUserWithEmailAndPassword(
            composableScope: CoroutineScope,
            onFinish: (Boolean) -> Unit,
            newUser: NewUser
        ) {
            composableScope.launch {
                val apiService = ApiServiceAuthentication.getInstance()

                try {
                    val result = apiService.register(newUser)
                    if (result.isSuccessful) {
                        createUser = result.body()!!
                        getCurrentUser(
                            composableScope = composableScope,
                            idOfUser = createUser.id,
                            onFinished = {
                                if(it)
                                    onFinish(true)
                                else
                                    onFinish(false)
                            },
                        )
                    }
                    else {
                        onFinish(false)
                    }
                } catch (e: Exception) {
                    onFinish(false)
                    errorMessage = e.message.toString()
                }
            }
        }

        private fun getCurrentUser(
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

        fun checkAllValidations(
            textEmail: String,
            textPassword: String,
            checkedStatePrivacyPolicies: Boolean
        ): Boolean {
            if (
                !isValidEmail(text = textEmail) ||
                !isValidPassword(text = textPassword) ||
                !checkedStatePrivacyPolicies
            )  return false
            return  true
        }
/*
        private fun setInformationUser(
            email: String,
            composableScope: CoroutineScope,
            onFinish:() -> Unit
        ) {
            createAppUser = appUser(
                courses =  mutableListOf<String>(),
                classes = mutableListOf<String>(),
                name = "userName",
                email = email,
                imgPath = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2FdefaultUserImg.png?alt=media&token=eb869349-7d2b-4b9a-b04a-b304c0366c78",
                id = "id",
                description = "myDescription"
            )

            composableScope.launch {
                val apiService = ApiServiceUser.getInstance()

                try {
                    val result = apiService.postUser(createAppUser)
                    if (result.isSuccessful) {
                        createAppUser = result.body()!!

                        onFinish()
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }*/

    }
}

/*
saveCurrentUser(
onFinished = {
    navController.navigate(Destinations.MainAppView.route)
}
)
*/
