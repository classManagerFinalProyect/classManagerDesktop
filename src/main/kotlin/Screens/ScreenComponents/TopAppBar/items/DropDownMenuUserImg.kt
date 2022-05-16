package Screens.MainAppScreen.Items

import Screens.ScreenComponents.TopAppBar.Account.MainAccount
import Screens.ScreenComponents.TopAppBar.Profile.mainProfile
import Screens.ScreenItems.Dialogs.defaultDialog
import Utils.AsyncImage
import Utils.loadImageBitmap
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.local.CurrentUser

@Composable
fun dropDownMenuUserImg(
    onCloseSession: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }
    var profileIsOpen by remember { mutableStateOf(false) }
    var accountIsOpen by remember { mutableStateOf(false) }

    if (profileIsOpen) {
        defaultDialog(
            content = { mainProfile() },
            title = "Mi Profile",
            onClose = { profileIsOpen = it},
            resizable = true,
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(900.dp, 700.dp)
            )
        )
    }
    if (accountIsOpen) {
        defaultDialog(
            content = {
                MainAccount(
                    onCloseSession = { onCloseSession() }
                )
            },
            title = "Mi Account",
            onClose = { accountIsOpen = it},
            resizable = true,
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(900.dp, 700.dp)
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center,
        content = {
            Box(
                content = {
                    AsyncImage(
                        load = { loadImageBitmap(CurrentUser.currentUser.imgPath) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "User Image",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = CircleShape
                            )
                            .onGloballyPositioned { coordinates ->
                                size = coordinates.size.toSize()
                            }
                            .clickable {
                                expanded = !expanded
                            },

                        contentScale = ContentScale.Crop,
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            profileIsOpen = true
                        },
                        content = {
                            Text(text = "Mi Perfil")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            accountIsOpen = true
                        },
                        content = {
                            Text(text = "Mi cuenta")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                        },
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {
                                        onCloseSession()

                                    },
                                content = {
                                    Icon(
                                        painter = painterResource(resourcePath = "logout_white.png"),
                                        contentDescription = "Cerrar sesión",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(3.dp))
                                    Text(text = "Cerrar sesión")
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}