package Screens.ScreenComponents.TopAppBar.Account

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Register.PrivacyPolicies.MainPrivacyPolicies
import Screens.ScreenComponents.TopAppBar.Profile.ViewModelProfile
import Screens.ScreenItems.Dialogs.defaultDialog
import Screens.ScreenItems.confirmAlertDialog
import Screens.ScreenItems.longButton
import Utils.AsyncImage
import Utils.loadImageBitmap
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.local.CurrentUser

@Composable
fun MainAccount(
    onCloseSession: () -> Unit
) {
    //Texts
    var (emailText,onValueChangeEmailText) = remember{ mutableStateOf(CurrentUser.currentUser.email) }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val emailOfUserError = "El correo debe de seguir un patrón: test@gmail.com"

    //Help variables
    var showPolicy by remember { mutableStateOf(false) }
    var deleteAccount by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()


    if (deleteAccount) {
        var title = "¿Seguro que desea eliminar su cuenta?"
        var subtitle = "No podrás volver a recuperarla. "

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack =  { deleteAccount = false },
            onClickAccept = {
                ViewModelAccount.deleteAccount(
                    uid = CurrentUser.currentUser.id,
                    composableScope = composableScope,
                    onFinish =  {
                        onCloseSession()
                    }
                )
            }

        )
    }

    if(showPolicy){
        defaultDialog(
            onClose = { showPolicy = it },
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Mi Cuenta")
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    LazyColumn (
                        modifier = Modifier.fillMaxSize(0.9f),
                        content = {
                            item {
                                bigTextFieldWithErrorMessage(
                                    text = "Email del usuario",
                                    value = emailText,
                                    onValueChange = onValueChangeEmailText,
                                    validateError = ViewModelAccount::isValidEmail,
                                    errorMessage = emailOfUserError,
                                    changeError = emailErrorChange,
                                    error = emailError,
                                    mandatory = false,
                                    KeyboardType = KeyboardType.Text,
                                    enabled = true
                                )
                            }
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(PaddingValues(start = 40.dp, end = 40.dp)),
                                    content = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start ,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp)
                                                .clickable {
                                                    showPolicy = true
                                                },
                                            content = {
                                                Spacer(modifier = Modifier.padding(5.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Person,
                                                    contentDescription = "Perfil"
                                                )
                                                Spacer(modifier = Modifier.padding(3.dp))
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.9f),
                                                    content = {
                                                        Text(text = "Politicas de privacidad")
                                                    }
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(PaddingValues(start = 40.dp, end = 40.dp)),
                                    content = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start ,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp)
                                                .clickable {
                                                    deleteAccount = true
                                                },
                                            content = {
                                                Spacer(modifier = Modifier.padding(5.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete Account"
                                                )
                                                Spacer(modifier = Modifier.padding(3.dp))
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.9f),
                                                    content = {
                                                        Text(text = "Delete my account")
                                                    }
                                                )
                                            }
                                        )
                                    }
                                )
                            }

                            item {
                                longButton(
                                    text = "Guardar cambios",
                                    onClick = {

                                    }
                                )
                            }
                        }
                    )
                }
            )
        }
    )

}

