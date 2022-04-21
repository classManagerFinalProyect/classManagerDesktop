package Screens.Register

import ScreenItems.bigPasswordInputWithErrorMesaje
import ScreenItems.bigTextFieldWithErrorMesaje
import Screens.Register.PrivacyPolicies.MainPrivacyPolicies
import Screens.Register.PrivacyPolicies.PrivacyPoliciesDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import com.example.classmanagerandroid.Views.Register.labelledCheckbox
import data.local.NewUser
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainRegister(
    onBack: () -> Unit
) {
    //Texts
    var (emailText,onValueChangeEmailText) = remember{ mutableStateOf("test@gmail.com") }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val nameOfEmailError = remember { mutableStateOf("El email no es válido: ejemplo@ejemplo.eje") }

    var (passwordText,onValueChangePasswordText) = remember{ mutableStateOf("11111111") }
    var (passwordError,passwordErrorChange) = remember { mutableStateOf(false) }
    val passwordTextErrorMesaje = remember { mutableStateOf("La contraseña no puede ser inferior a 8 caracteres ni contener caracteres especiales") }

    var (repeatPasswordText,onValueChangeRepeatPasswordText) = remember{ mutableStateOf("11111111") }
    var (repeatPasswordError,repeatPasswordErrorChange) = remember { mutableStateOf(false) }
    val repeatPasswordTextErrorMesaje = remember { mutableStateOf("La contraseña no puede ser inferior a 8 caracteres ni contener caracteres especiales") }

    //Help variables
    val (checkedStatePrivacyPolicies,onValueChangecheckedStatePrivacyPolicies) = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()
    val composableScope = rememberCoroutineScope()
    var isOpen by remember { mutableStateOf(false) }


    if(isOpen) {
        PrivacyPoliciesDialog(
            onClose = { isOpen = it }
        )
    }


    Scaffold(
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
                                    bigTextFieldWithErrorMesaje(
                                        text = "Email",
                                        value = emailText,
                                        onValueChange = onValueChangeEmailText,
                                        validateError =  ::isValidEmail,
                                        errorMesaje = nameOfEmailError.value,
                                        changeError = emailErrorChange,
                                        error = emailError,
                                        mandatory = true,
                                        KeyboardType = KeyboardType.Text
                                    )
                                }

                                item {
                                    bigPasswordInputWithErrorMesaje(
                                        value = passwordText,
                                        onValueChangeValue = onValueChangePasswordText,
                                        valueError = passwordError,
                                        onValueChangeError = passwordErrorChange,
                                        errorMesaje = passwordTextErrorMesaje.value,
                                        validateError = ::isValidPassword,
                                        mandatory = true,
                                        keyboardType = KeyboardType.Text
                                    )
                                }
                                item {
                                    bigPasswordInputWithErrorMesaje(
                                        value = repeatPasswordText,
                                        onValueChangeValue = onValueChangeRepeatPasswordText,
                                        valueError = repeatPasswordError,
                                        onValueChangeError = repeatPasswordErrorChange,
                                        errorMesaje = repeatPasswordTextErrorMesaje.value,
                                        validateError = ::isValidPassword,
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
                                            if (repeatPasswordText.equals(passwordText)) {
                                                if (
                                                    checkAllValidations(
                                                        textEmail = emailText,
                                                        textPassword = passwordText,
                                                        checkedStatePrivacyPolicies = checkedStatePrivacyPolicies
                                                    )
                                                ) {


                                                    ViewModelRegister.createUserWithEmailAndPassword (
                                                        composableScope = composableScope,
                                                        newUser = NewUser("", emailText, passwordText),
                                                        onFinish = {
                                                            snackbarCoroutineScope.launch {
                                                                scaffoldState.snackbarHostState.showSnackbar("La cuenta se ha creado correctamente")
                                                            }
                                                        }
                                                    )

                                                }
                                                else {
                                                    snackbarCoroutineScope.launch {
                                                        scaffoldState.snackbarHostState.showSnackbar("Debes rellenar todos los campos correctamente")
                                                    }
                                                }
                                            }
                                            else {
                                                snackbarCoroutineScope.launch {
                                                    scaffoldState.snackbarHostState.showSnackbar("Las claves deben ser iguales\"")
                                                }
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



//Validaciones
fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
fun isValidPassword(text: String) = Pattern.compile("^[a-zA-Z0-9_]{8,}\$", Pattern.CASE_INSENSITIVE).matcher(text).find()

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