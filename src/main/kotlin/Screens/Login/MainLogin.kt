package Screens.Login

import ScreenItems.bigPasswordInput
import ScreenItems.bigPasswordInputWithErrorMessage
import ScreenItems.bigTextFieldWithErrorMessage
import Screens.ScreenItems.Dialogs.infoDialog
import Screens.ScreenItems.Dialogs.loadingDialog
import Screens.ScreenItems.Others.floatToast
import Utils.CommonErrors
import Utils.LazyGridFor
import Utils.isValidEmail
import Utils.isValidPassword
import akka.http.scaladsl.model.headers.LinkParams
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.database.utilities.Validation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.SystemColor.text
import java.util.regex.Pattern

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainLogin(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    //Help variables
    val composableScope = rememberCoroutineScope()
    var showToast = remember { mutableStateOf(false) }
    var textToast = remember { mutableStateOf("") }
    var loading = remember { mutableStateOf(false) }
    var loadingError = remember { mutableStateOf(false) }
    var showAlertToast = remember { mutableStateOf(false) }
    var getUser by remember{ mutableStateOf(true) }

    //Texts
    var emailText by remember{ mutableStateOf("test@gmail.com") }
    var emailError by remember { mutableStateOf(false) }

    var passwordText by remember{ mutableStateOf("11111111") }
    var passwordError by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf(TextFieldValue()) }

    if(getUser) {
        ViewModelLogin.getUsers(
            composableScope = composableScope,
            onFinished = {
                getUser = false
            }
        )
        getUser = false
    }

    LaunchedEffect(loadingError.value) {
        if(loadingError.value) {
            delay(9000)
            if(loading.value) {
                loading.value = false
                showAlertToast.value =  true
            }
            loadingError.value = false
        }
    }

    if(showAlertToast.value) {
        infoDialog(
            showToast = showAlertToast,
            title = "No se ha podido realizar el logeo",
            text = "Asegurese de que los datos introducidos son correctos y vuelva a intentarlo"
        )
    }


    LaunchedEffect(showToast.value) {
        if (showToast.value) {
            delay(1800L)
            showToast.value = false
        }
    }

    if(loading.value) {
        loadingDialog(
            loading = loading,
            informativeText = "Iniciando sesión"
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
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Login")
                                },
                            )
                        },
                        floatingActionButtonPosition = FabPosition.Center,
                        floatingActionButton = {
                            if(showToast.value) {
                                floatToast(
                                    message = textToast.value,
                                    showToast = showToast
                                )
                            }
                        },
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
                                            onValueChange = { emailText = it },
                                            validateError = ::isValidEmail,
                                            errorMessage = CommonErrors.notValidEmail,
                                            changeError = {emailError = it},
                                            error = emailError,
                                            mandatory = false,
                                            KeyboardType = KeyboardType.Email,
                                            enabled = true
                                        )
                                    }

                                    item {
                                        bigPasswordInputWithErrorMessage(
                                            value = passwordText,
                                            onValueChangeValue = { passwordText = it },
                                            valueError = passwordError,
                                            onValueChangeError = { passwordError = it },
                                            errorMessage = CommonErrors.notValidPassword,
                                            validateError = { isValidPassword(it) },
                                            mandatory = false,
                                            keyboardType = KeyboardType.Text,
                                        )
                                    }

                                    item {
                                        Button(
                                            content = {
                                                Text(text = "Iniciar sesión")
                                            },
                                            onClick = {
                                                if(isValidPassword(passwordText) && isValidEmail(emailText)) {
                                                    loadingError.value = true
                                                    loading.value = true
                                                    ViewModelLogin.login(
                                                        composableScope = composableScope,
                                                        email = emailText,
                                                        password =  passwordText,
                                                        onFinished = {
                                                            if(it) {
                                                                loading.value = false
                                                                onLoginClick()
                                                            }
                                                            else{
                                                                loading.value = false
                                                                textToast.value = "ERROR: No se ha podido logear correctamente"
                                                                showToast.value = true
                                                            }
                                                        }
                                                    )
/*
                                                    if(ViewModelLogin.isValidUser(emailText, passwordText)) {



                                                        ViewModelLogin.getCurrentUser(
                                                            composableScope = composableScope,
                                                            idOfUser = ViewModelLogin.user.id,
                                                            onFinished = {
                                                                if(it) {
                                                                    loading.value = false
                                                                    onLoginClick()
                                                                }
                                                                else{
                                                                    loading.value = false
                                                                    textToast.value = "ERROR: No se ha podido logear correctamente"
                                                                    showToast.value = true
                                                                }
                                                            },
                                                        )

                                                    }
                                                    else {
                                                        textToast.value = "ERROR: El usuario no existe"
                                                        showToast.value = true
                                                        loadingError.value = false
                                                        loading.value = false
                                                    }
                                                    */
                                                }
                                                else {
                                                    textToast.value = "ERROR: ${CommonErrors.incompleteFields}"
                                                    showToast.value = true
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }
                                    /*
                                    item {
                                        Text(
                                            text = "O bien",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }
                                    item {
                                        Button(
                                            content = {
                                                Text(text = "Iniciar sesión con Google")
                                            },
                                            onClick = {
                                                //Inicair sesi´çon conm google

                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }
                                    */

                                    item {
                                        Spacer(Modifier.padding(8.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        ) {
                                            /*
                                            Text(
                                                text = "He olvidado la contraseña",
                                                color = Color.Blue,
                                                modifier = Modifier
                                                    .clickable {
                                                        onForgotPasswordClick()
                                                    }
                                            )
                                            */
                                            Text(
                                                text = "Crear usuario",
                                                color = Color.Blue,
                                                modifier = Modifier
                                                    .clickable {
                                                        onRegisterClick()
                                                    }
                                            )
                                        }
                                    }
                                }
                            )
                        },
                    )
                }
            )
        }
    )

}
