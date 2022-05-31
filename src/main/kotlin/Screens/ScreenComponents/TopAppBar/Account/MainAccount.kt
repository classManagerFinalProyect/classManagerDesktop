package Screens.ScreenComponents.TopAppBar.Account

import Screens.Register.PrivacyPolicies.MainPrivacyPolicies
import Screens.ScreenItems.Dialogs.defaultDialog
import Screens.ScreenItems.Dialogs.confirmAlertDialog
import Screens.ScreenItems.Buttons.longButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.local.CurrentUser

@Composable
fun MainAccount(
    onCloseSession: () -> Unit,
    onCloseDialog: (Boolean) -> Unit
) {

    //Help variables
    var showPolicy by remember { mutableStateOf(false) }
    var deleteAccount by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()


    if (deleteAccount) {
        val title = "¿Seguro que desea eliminar su cuenta?"
        val subtitle = "No podrás volver a recuperarla. "

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
            Spacer(modifier = Modifier.padding(20.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    LazyColumn (
                        modifier = Modifier.fillMaxSize(0.9f),
                        content = {
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
                                                .height(50.dp),
                                            content = {
                                                Spacer(modifier = Modifier.padding(5.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Email,
                                                    contentDescription = "Email"
                                                )
                                                Spacer(modifier = Modifier.padding(3.dp))
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.9f),
                                                    content = {
                                                        Text(text = "Email: ${CurrentUser.currentUser.email}")
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
                                    text = "Aceptar",
                                    onClick = {
                                        onCloseDialog(false)
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

