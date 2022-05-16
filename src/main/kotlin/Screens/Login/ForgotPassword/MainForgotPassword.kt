package Screens.Login.ForgotPassword

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Login.ViewModelLogin
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun MainForgotPassword(
    onLoginClick: () -> Unit
) {
    //Texts
    var (emailText,onValueChangeEmailText) = remember{ mutableStateOf("sainero2002dani.j@gmail.com") }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val nameOfEmailError = remember { mutableStateOf("El email no es válido: ejemplo@ejemplo.eje") }

    //Help variables
    val composableScope = rememberCoroutineScope()


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
                                    Text(text = "Recuperar tu cuenta")
                                }
                            )
                        },
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(BorderStroke(1.dp, Color.LightGray)),
                                content = {
                                    Text(
                                        text = "Introduce tu correo electrónico para buscar tu cuenta",
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .padding(
                                                PaddingValues(
                                                    start = 40.dp,
                                                    end = 40.dp,
                                                    top = 10.dp,
                                                    bottom = 3.dp
                                                )
                                            )
                                    )
                                    bigTextFieldWithErrorMessage(
                                        text = "Email",
                                        value = emailText,
                                        onValueChange = onValueChangeEmailText,
                                        validateError = ViewModelLogin::isValidEmail,
                                        errorMessage = nameOfEmailError.value,
                                        changeError = emailErrorChange,
                                        error = emailError,
                                        mandatory = true,
                                        KeyboardType = KeyboardType.Text,
                                        enabled = true
                                    )

                                    Button(
                                        content = {
                                            Text(text = "Enviar correo para cambiar su contraseña")
                                        },
                                        onClick = {
                                            ViewModelLogin.sendEmailToChangePassword(
                                                email = emailText,
                                                composableScope = composableScope,
                                                onFinished =  {

                                                }
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                    )
                                    TextButton(
                                        onClick = {
                                            onLoginClick()
                                        },
                                        content = {
                                            Text(text = "Iniciar sesión")
                                        }
                                    )

                                }
                            )
                        }
                    )
                }
            )
        }
    )
}