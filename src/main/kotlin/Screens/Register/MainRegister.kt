package Screens.Register

import ScreenItems.bigPasswordInputWithErrorMessage
import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Register.PrivacyPolicies.MainPrivacyPolicies
import Screens.Register.ViewModelRegister.Companion.checkAllValidations
import Screens.ScreenItems.Dialogs.defaultDialog
import Screens.ScreenItems.Dialogs.loadingDialog
import Screens.ScreenItems.Others.floatToast
import Utils.CommonErrors
import Utils.createSha256
import Utils.isValidEmail
import Utils.isValidPassword
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import com.example.classmanagerandroid.Views.Register.labelledCheckbox
import data.local.NewUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainRegister(
    onBack: () -> Unit,
    onLogin: () -> Unit
) {
    //Texts
    var (emailText,onValueChangeEmailText) = remember{ mutableStateOf("test@gmail.com") }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }

    var (passwordText,onValueChangePasswordText) = remember{ mutableStateOf("11111111") }
    var (passwordError,passwordErrorChange) = remember { mutableStateOf(false) }

    var (repeatPasswordText,onValueChangeRepeatPasswordText) = remember{ mutableStateOf("11111111") }
    var (repeatPasswordError,repeatPasswordErrorChange) = remember { mutableStateOf(false) }

    //Help variables
    val (checkedStatePrivacyPolicies,onValueChangecheckedStatePrivacyPolicies) = remember { mutableStateOf(true) }
    val composableScope = rememberCoroutineScope()
    var isOpen by remember { mutableStateOf(false) }
    var toastMessage = remember { mutableStateOf("") }
    var showToast = remember { mutableStateOf(false) }
    var loading = remember { mutableStateOf(false) }



    LaunchedEffect(showToast.value) {
        if (showToast.value) {
            delay(1800L)
            showToast.value = false
        }
    }

    if(loading.value) {
        loadingDialog(
            loading = loading,
            informativeText = "Creando usuario"
        )
    }

    if(isOpen) {
        defaultDialog(
            onClose = { isOpen = it },
            title = "Privacy Policies",
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(800.dp, 600.dp)
            ),
            resizable = false,
            content = {
                MainPrivacyPolicies()
            }
        )
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(500.dp)
                    .height(700.dp)
                    .border(1.dp, Color.LightGray),
                content = {
                    Scaffold(
                        floatingActionButton = {
                            if(showToast.value) {
                                floatToast(
                                    message = toastMessage.value,
                                    showToast = showToast
                                )
                            }
                        },
                        floatingActionButtonPosition = FabPosition.Center,
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Crear cuenta")
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            onBack()
                                        },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "Go back",
                                                tint = Color.White,
                                            )
                                        }
                                    )
                                }
                            )
                        },
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement =  Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .width(500.dp)
                                        .height(700.dp)
                                        .border(1.dp, Color.LightGray),
                                    content = {
                                        LazyColumn(
                                            content = {
                                                item {
                                                    Image(
                                                        painter = painterResource("logoDani.png"),
                                                        contentDescription = "Sample",
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                }

                                                item {
                                                    bigTextFieldWithErrorMessage(
                                                        text = "Email",
                                                        value = emailText,
                                                        onValueChange = onValueChangeEmailText,
                                                        validateError = { isValidEmail(it) },
                                                        errorMessage = CommonErrors.notValidEmail,
                                                        changeError = emailErrorChange,
                                                        error = emailError,
                                                        mandatory = true,
                                                        KeyboardType = KeyboardType.Text,
                                                        enabled = true
                                                    )
                                                }

                                                item {
                                                    bigPasswordInputWithErrorMessage(
                                                        value = passwordText,
                                                        onValueChangeValue = onValueChangePasswordText,
                                                        valueError = passwordError,
                                                        onValueChangeError = passwordErrorChange,
                                                        errorMessage = CommonErrors.notValidPassword,
                                                        validateError = { isValidPassword(it) },
                                                        mandatory = true,
                                                        keyboardType = KeyboardType.Text,
                                                    )
                                                }
                                                item {
                                                    bigPasswordInputWithErrorMessage(
                                                        value = repeatPasswordText,
                                                        onValueChangeValue = onValueChangeRepeatPasswordText,
                                                        valueError = repeatPasswordError,
                                                        onValueChangeError = repeatPasswordErrorChange,
                                                        errorMessage = CommonErrors.notValidPassword,
                                                        validateError = { isValidPassword(it) },
                                                        mandatory = true,
                                                        keyboardType = KeyboardType.Text
                                                    )
                                                }

                                                item {
                                                    Row (
                                                        content = {
                                                            labelledCheckbox(
                                                                labelText = "Politicas de Privacidad",
                                                                isCheckedValue = checkedStatePrivacyPolicies,
                                                                onValueChangeCheked = onValueChangecheckedStatePrivacyPolicies,
                                                                onClickText = {
                                                                    isOpen = true
                                                                }
                                                            )
                                                        }
                                                    )

                                                }
                                                item {
                                                    Button(
                                                        content = {
                                                            Text(text = "Registrarse")
                                                        },
                                                        onClick = {
                                                            if (
                                                                checkAllValidations(
                                                                    textEmail = emailText,
                                                                    textPassword = passwordText,
                                                                    checkedStatePrivacyPolicies = checkedStatePrivacyPolicies
                                                                )
                                                            ) {
                                                                if (repeatPasswordText == passwordText) {
                                                                    loading.value = true
                                                                    ViewModelRegister.createUserWithEmailAndPassword (
                                                                        composableScope = composableScope,
                                                                        newUser = NewUser("", emailText, createSha256(base = passwordText)!!),
                                                                        onFinish = {
                                                                            if(it) {
                                                                                loading.value = false
                                                                                toastMessage.value = "La cuenta se ha creado correctamente"
                                                                                showToast.value = true
                                                                                onLogin()
                                                                            }
                                                                            else {
                                                                                loading.value = false
                                                                                toastMessage.value = "ERROR: La cuenta no ha podido ser creada,pruebe otro usuario"
                                                                                showToast.value = true
                                                                            }


                                                                            /*
                                                                            snackbarCoroutineScope.launch {
                                                                                scaffoldState.snackbarHostState.showSnackbar("La cuenta se ha creado correctamente")
                                                                            }*/
                                                                        }
                                                                    )
                                                                }
                                                                else {
                                                                    toastMessage.value = "ERROR: Las claves deben ser iguales"
                                                                    showToast.value = true
                                                                }
                                                            }
                                                            else {
                                                                toastMessage.value = "ERROR: ${CommonErrors.incompleteFields}"
                                                                showToast.value = true
                                                            }
                                                        },
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                                    )
                                                }
                                            }
                                        )
                                    }
                                )
                            }
                        },
                    )
                }
            )
        }
    )


}









/*
   Window(
       onCloseRequest = { onCloseRequest(true) },
       content = {
           MainPrivacyPolicies(
               onBack = {}
           )
       }
   )

       if (isAskingToClose) {
           Dialog(
               onCloseRequest = { onCloseRequest(false) },
               title = "Close the document without saving?",
           ) {
               Button(
                   onClick = { onClickYes(false) }
               ) {
                   Text("Yes")
               }
           }

   }
*/